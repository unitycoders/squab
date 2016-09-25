package com.fossgalaxy.bot.examples;

import com.fossgalaxy.bot.Command;
import com.fossgalaxy.bot.misc.AnnotationModule;

import java.util.Map;

/**
 * Created by webpigeon on 25/09/16.
 */
public class HelloWorld extends AnnotationModule {

    public HelloWorld() {
        super("hello");
    }

    @Command("hello")
    public String myCommand(Map map, Object request){
        return "hello world";
    }

}
