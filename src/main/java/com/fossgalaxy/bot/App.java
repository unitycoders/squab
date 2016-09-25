package com.fossgalaxy.bot;

import com.fossgalaxy.bot.api.*;
import com.fossgalaxy.bot.api.module.ModuleCatalogue;
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

        //build a context for the dummy requests
        Context ctx = new DefaultContext();
        ctx.put(Context.USER, "testUser");
        ctx.put("modules", cfg.getList(String.class, "modules"));

        CommandParser parser = new CommandParser();
        System.out.println(parser.parse("hello world"));
        System.out.println(parser.parse("hello 'world with space'"));
        System.out.println(parser.parse("hello \"world with space\""));


        String[] inputs = {"hello hello", "hello helloTemplate", "hello helloFormat", "notDefined hello", "hello notDefined", "hello", ""};
        for (String input : inputs) {
            try {
                doRequest(ctx, parser.parse(input), catalogue);
            } catch (InvalidRequestException ex) {
                System.out.println(String.format("got %s (%s) when processing '%s'", ex.getClass(), ex.getMessage(), input));
            }
        }
    }

    public static void doRequest(Context context, Request request, ModuleCatalogue catalogue) {

        Module module = catalogue.get(request.getModule());
        if (module == null) {
            throw new InvalidRequestException(request.getModule());
        }

        Response resp = module.execute(context, request);
        System.out.println(resp.getOutput());
    }
}
