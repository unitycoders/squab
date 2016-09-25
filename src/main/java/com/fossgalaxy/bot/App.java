package com.fossgalaxy.bot;

import com.fossgalaxy.bot.api.Module;
import com.fossgalaxy.bot.api.module.ModuleCatalogue;
import com.fossgalaxy.bot.backend.*;
import com.fossgalaxy.bot.backend.netty.*;
import com.fossgalaxy.bot.config.ConfigFactory;
import com.fossgalaxy.bot.examples.IRCModule;
import com.fossgalaxy.bot.impl.processor.CommandParser;
import com.fossgalaxy.bot.model.MUCStorage;
import com.fossgalaxy.bot.model.MultiUserChat;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.configuration2.ImmutableConfiguration;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final List<Backend> backends = new ArrayList<>();

    public static void main( String[] args ) {
        //read in our configuration file
        ImmutableConfiguration cfg = ConfigFactory.getConfiguration("config.properties");
        List<String> modules = cfg.getList(String.class, "modules");

        //create a place to store the modules and load them in
        ModuleCatalogue catalogue = new ModuleCatalogue();
        modules.forEach(catalogue::load);

        //create an interactive prompt for the bot
        Dispatcher dispatcher = new Dispatcher(new CommandParser(), catalogue);

        final MUCStorage store = new MUCStorage();

        EventDispatcher<IRCEvent> ircEvent = new EventDispatcher<>();

        ircEvent.register("JOIN", (ircEvent1 -> {
            MultiUserChat muc = store.getChat("irc", ircEvent1.args.get(0));
            String nick = IRCEventHandler.mask2Nick(ircEvent1.prefix);
            muc.addUser(nick);
        }));

        ircEvent.register("PART", (ircEvent1 -> {
            MultiUserChat muc = store.getChat("irc", ircEvent1.args.get(0));
            String nick = IRCEventHandler.mask2Nick(ircEvent1.prefix);
            muc.removeUser(nick);
        }));

        ircEvent.register("QUIT", (ircEvent1 -> {
            String nick = IRCEventHandler.mask2Nick(ircEvent1.prefix);
            store.removeFromAll(nick);
        }));

        ircEvent.register("353", (ircEvent1 -> {

            String[] nicks = ircEvent1.args.get(3).split(" ");
            System.out.println("got IRC names: "+ Arrays.toString(nicks));

            MultiUserChat room = store.getChat("irc", ircEvent1.args.get(2));
            for (String nick : nicks) {
                room.addUser(nick);
            }
        }));


        //backends.add(new TelnetBackend(dispatcher));
        //backends.add(new NettyTelnetServerBackend(1337, dispatcher));
        //backends.add(new ConsoleBackend(dispatcher));
        backends.add(new NettyIRCClientBackend("irc.freenode.net", 6667, ircEvent, dispatcher));

        Module ircModule = new IRCModule(backends.get(0), store);
        ircModule.init();
        catalogue.register("irc", ircModule);

        for (Backend backend : backends) {
            new Thread(backend).start();
        }
    }

    public static void shutdownBot() {
        for (Backend backend : backends) {
            backend.terminate();
        }
    }
}
