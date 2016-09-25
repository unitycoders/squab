package com.fossgalaxy.bot.examples;

import com.fossgalaxy.bot.api.Command;
import com.fossgalaxy.bot.api.Context;
import com.fossgalaxy.bot.api.Request;
import com.fossgalaxy.bot.api.Response;
import com.fossgalaxy.bot.api.module.AnnotationModule;
import com.fossgalaxy.bot.api.module.Shortcuts;

import java.util.Arrays;

/**
 * Created by webpigeon on 25/09/16.
 */
public class HelloWorld extends AnnotationModule {

    public HelloWorld() {
        super("hello");
    }

    @Command("hello")
    public Response doHello(Context map, Request request){
        return Shortcuts.respond("hello world");
    }

    @Command("helloFormat")
    public Response doFormat(Context map, Request request){
        return Shortcuts.respondFormatted("hello %s", map.get(Context.USER));
    }

    @Command("helloTemplate")
    public Response doTemplate(Context map, Request request){
        map.put("data", Utils.chooseOne(Arrays.asList("red", "orange", "blue", "purple")));

        return Shortcuts.respondWith("hello ${user}, Today's colour is ${data}.", map);
    }

}
