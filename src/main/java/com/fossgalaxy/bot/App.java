package com.fossgalaxy.bot;

import com.fossgalaxy.bot.api.module.ModuleCatalogue;
import com.fossgalaxy.bot.backend.*;
import com.fossgalaxy.bot.backend.netty.NettyTelnetServerBackend;
import com.fossgalaxy.bot.config.ConfigFactory;
import com.fossgalaxy.bot.impl.processor.CommandParser;
import org.apache.commons.configuration2.ImmutableConfiguration;

import java.util.ArrayList;
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

        //backends.add(new TelnetBackend(dispatcher));
        backends.add(new NettyTelnetServerBackend(1337, dispatcher));
        backends.add(new ConsoleBackend(dispatcher));

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
