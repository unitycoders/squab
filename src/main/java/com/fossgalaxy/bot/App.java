package com.fossgalaxy.bot;

import com.fossgalaxy.bot.api.Module;
import com.fossgalaxy.bot.api.module.ModuleCatalogue;
import com.fossgalaxy.bot.backend.*;
import com.fossgalaxy.bot.backend.netty.*;
import com.fossgalaxy.bot.backend.netty.irc.*;
import com.fossgalaxy.bot.config.ConfigFactory;
import com.fossgalaxy.bot.examples.IRCModule;
import com.fossgalaxy.bot.impl.processor.CommandParser;
import com.fossgalaxy.bot.impl.processor.CommandProcessorModule;
import com.fossgalaxy.bot.model.MUCStorage;
import com.fossgalaxy.bot.model.ModelModule;
import com.fossgalaxy.bot.model.MultiUserChat;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
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

    public static void main( String[] args ) {
        //read in our configuration file
        ImmutableConfiguration cfg = ConfigFactory.getConfiguration("config.properties");
        List<String> modules = cfg.getList(String.class, "modules");

        //Create injector
        Injector injector = Guice.createInjector(new IRCBackend(), new ModelModule(), new CommandProcessorModule());

        //create a place to store the modules and load them in
        ModuleCatalogue catalogue = injector.getInstance(ModuleCatalogue.class);
        modules.forEach(catalogue::load);

        //IRC housekeeping stuff
        injector.getInstance(ChannelMonitor.class);

        //backends.add(new TelnetBackend(dispatcher));
        //backends.add(new NettyTelnetServerBackend(1337, dispatcher));
        //backends.add(new ConsoleBackend(dispatcher));

        List<Backend> backends = new ArrayList<>();

        NettyIRCClientBackend ircBackend = injector.getInstance(NettyIRCClientBackend.class);
        ircBackend.connect("irc.freenode.net", 6667);
        backends.add(ircBackend);


        Module ircModule = new IRCModule();
        catalogue.register("irc", ircModule);

        for (Backend backend : backends) {
            new Thread(backend).start();
        }
    }

}
