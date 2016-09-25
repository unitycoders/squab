package com.fossgalaxy.bot;

import com.fossgalaxy.bot.api.Context;
import com.fossgalaxy.bot.api.Module;
import com.fossgalaxy.bot.api.module.ModuleCatalogue;
import com.fossgalaxy.bot.config.ConfigFactory;
import com.fossgalaxy.bot.impl.DefaultContext;
import com.fossgalaxy.bot.impl.DefaultRequest;
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

        //build a context for the dummy requests
        Context ctx = new DefaultContext();
        ctx.put(Context.USER, "testUser");
        ctx.put("modules", cfg.getList(String.class, "modules"));

        //test that a dummy call works
        Module helloModule = catalogue.get("hello");

        System.out.println( helloModule.execute(ctx, new DefaultRequest("hello", "hello")) );
        System.out.println( helloModule.execute(ctx, new DefaultRequest("hello", "helloTemplate")) );
        System.out.println( helloModule.execute(ctx, new DefaultRequest("hello", "helloFormat")) );
    }
}
