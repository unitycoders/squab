package com.fossgalaxy.bot.examples;

import com.fossgalaxy.bot.App;
import com.fossgalaxy.bot.api.Command;
import com.fossgalaxy.bot.api.Context;
import com.fossgalaxy.bot.api.Request;
import com.fossgalaxy.bot.api.module.AnnotationModule;

/**
 * Created by webpigeon on 25/09/16.
 */
public class AdminModule extends AnnotationModule {

    public AdminModule() {
        super("admin");
    }

    @Command("shutdown")
    public void onShutdown(Context ctx, Request request) {
        App.shutdownBot();
    }
}
