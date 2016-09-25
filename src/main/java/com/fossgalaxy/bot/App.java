package com.fossgalaxy.bot;

import com.fossgalaxy.bot.api.Module;
import com.fossgalaxy.bot.api.module.ModuleCatalogue;
import com.fossgalaxy.bot.backend.*;
import com.fossgalaxy.bot.backend.netty.*;
import com.fossgalaxy.bot.backend.netty.irc.ChannelMonitor;
import com.fossgalaxy.bot.backend.netty.irc.IRCEvent;
import com.fossgalaxy.bot.backend.netty.irc.IRCEventHandler;
import com.fossgalaxy.bot.backend.netty.irc.NettyIRCClientBackend;
import com.fossgalaxy.bot.config.ConfigFactory;
import com.fossgalaxy.bot.examples.IRCModule;
import com.fossgalaxy.bot.impl.processor.CommandParser;
import com.fossgalaxy.bot.model.MUCStorage;
import com.fossgalaxy.bot.model.MultiUserChat;
import org.apache.commons.configuration2.ImmutableConfiguration;

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

        //IRC housekeeping stuff
        EventDispatcher<IRCEvent> ircDispatcher = new EventDispatcher<>();
        ChannelMonitor ircChannelMonitor = new ChannelMonitor(store);
        ircChannelMonitor.bind(ircDispatcher);

        //show privmsg events in terminal (demo IRC specific callbacks)
        ircDispatcher.register("PRIVMSG", event -> System.out.println(event) );
        ircDispatcher.register("NOTICE", event -> System.out.println(event) );

        //backends.add(new TelnetBackend(dispatcher));
        //backends.add(new NettyTelnetServerBackend(1337, dispatcher));
        //backends.add(new ConsoleBackend(dispatcher));
        backends.add(new NettyIRCClientBackend("irc.freenode.net", 6667, ircDispatcher, dispatcher));

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
