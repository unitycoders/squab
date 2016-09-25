package com.fossgalaxy.bot;

import com.fossgalaxy.bot.api.*;
import com.fossgalaxy.bot.api.module.ModuleCatalogue;
import com.fossgalaxy.bot.backend.ConsoleBackend;
import com.fossgalaxy.bot.backend.Dispatcher;
import com.fossgalaxy.bot.config.ConfigFactory;
import com.fossgalaxy.bot.impl.DefaultContext;
import com.fossgalaxy.bot.impl.DefaultRequest;
import com.fossgalaxy.bot.impl.processor.CommandParser;
import org.apache.commons.configuration2.ImmutableConfiguration;

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

        //create a place to store the modules and load them in
        ModuleCatalogue catalogue = new ModuleCatalogue();
        modules.forEach(catalogue::load);

        //create an interactive prompt for the bot
        Dispatcher dispatcher = new Dispatcher(new CommandParser(), catalogue);
        ConsoleBackend backend = new ConsoleBackend(dispatcher);
        backend.run();
    }
}
