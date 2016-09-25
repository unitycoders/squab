package com.fossgalaxy.bot;

import com.fossgalaxy.bot.examples.HelloWorld;
import com.fossgalaxy.bot.misc.AnnotationModule;
import com.fossgalaxy.bot.misc.Module;
import com.fossgalaxy.bot.misc.ModuleCatalogue;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Module module = new AnnotationModule("test");

        Module hello = new HelloWorld();
        hello.init();

        ModuleCatalogue catalogue = new ModuleCatalogue();
        catalogue.register("hello", hello );

        System.out.println( catalogue.get("hello").execute(null, null) );
    }
}
