package com.fossgalaxy.bot.examples;

import com.fossgalaxy.bot.api.Command;
import com.fossgalaxy.bot.api.Context;
import com.fossgalaxy.bot.api.Request;
import com.fossgalaxy.bot.api.Response;
import com.fossgalaxy.bot.api.module.AnnotationModule;
import com.fossgalaxy.bot.api.module.Shortcuts;
import com.fossgalaxy.bot.backend.Backend;
import com.fossgalaxy.bot.backend.netty.irc.NettyIRCClientBackend;
import com.fossgalaxy.bot.model.MUCStorage;
import com.fossgalaxy.bot.model.MultiUserChat;
import com.google.inject.Inject;

/**
 * Created by webpigeon on 25/09/16.
 */
public class IRCModule extends AnnotationModule {
    private Backend ircBackend;
    private MUCStorage store;

    public IRCModule() {
        super("irc");
    }

    @Inject
    public void injectRequirements(NettyIRCClientBackend backend, MUCStorage store) {
        this.ircBackend = backend;
        this.store = store;
    }

    @Command("raw")
    public Response doRaw(Context ctx, Request request) {
        String message = request.getArgument(0);
        ircBackend.sendRaw(message);
        return Shortcuts.respondSuccess();
    }

    @Command("users")
    public Response doUsers(Context ctx, Request request) {
        String roomName = request.getArgument(0);
        MultiUserChat room = store.getChat("irc", roomName);
        return Shortcuts.respondFormatted("users are: %s", String.join(", ", room.users));
    }

    @Command("channels")
    public Response doChannels(Context ctx, Request request) {
        return Shortcuts.respondFormatted("channels are: %s", String.join(", ", store.getKeys()));
    }
}
