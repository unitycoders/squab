package com.fossgalaxy.bot;

import com.fossgalaxy.bot.config.ConfigFactory;
import com.fossgalaxy.bot.examples.HelloWorld;
import com.fossgalaxy.bot.misc.AnnotationModule;
import com.fossgalaxy.bot.misc.Module;
import com.fossgalaxy.bot.misc.ModuleCatalogue;
import org.apache.commons.configuration2.Configuration;
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

        //test that a dummy call works
        System.out.println( catalogue.get("hello").execute(null, null) );
    }
}
