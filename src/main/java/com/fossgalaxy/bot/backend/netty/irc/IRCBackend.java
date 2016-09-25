package com.fossgalaxy.bot.backend.netty.irc;

import com.fossgalaxy.bot.backend.Backend;
import com.fossgalaxy.bot.backend.netty.EventDispatcher;
import com.google.inject.AbstractModule;

/**
 * Created by webpigeon on 25/09/16.
 */
public class IRCBackend extends AbstractModule {
    @Override
    protected void configure() {
        bind(EventDispatcher.class).toInstance(new EventDispatcher());
        bind(Backend.class).to(NettyIRCClientBackend.class);
    }
}
