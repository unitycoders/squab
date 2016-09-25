package com.fossgalaxy.bot.examples;

import com.fossgalaxy.bot.api.Command;
import com.fossgalaxy.bot.api.Context;
import com.fossgalaxy.bot.api.Request;
import com.fossgalaxy.bot.api.Response;
import com.fossgalaxy.bot.api.module.AnnotationModule;
import com.fossgalaxy.bot.api.module.Shortcuts;
import com.fossgalaxy.bot.backend.Backend;

/**
 * Created by webpigeon on 25/09/16.
 */
public class IRCModule extends AnnotationModule {
    private Backend ircBackend;

    public IRCModule(Backend ircBackend) {
        super("irc");
        this.ircBackend = ircBackend;
    }

    @Command("raw")
    public Response doRaw(Context ctx, Request request) {
        String message = request.getArgument(0);
        ircBackend.sendRaw(message);
        return Shortcuts.respondSuccess();
    }
}
