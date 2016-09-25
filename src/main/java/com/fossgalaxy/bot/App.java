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
    public static void main( String[] args ) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        ImmutableConfiguration cfg = ConfigFactory.getConfiguration();
        List<String> modules = cfg.getList(String.class, "modules");
        System.out.println(modules);
        System.out.println(cfg.getString("username"));


        Module hello = new HelloWorld();
        hello.init();

        ModuleCatalogue catalogue = new ModuleCatalogue();
        for (String moduleName : modules) {
            Class<?> moduleClazz = Class.forName(moduleName);
            Module module = (Module)moduleClazz.newInstance();
            module.init();
            catalogue.register(module.getName(), module);
        }

        System.out.println( catalogue.get("hello").execute(null, null) );
    }
}
