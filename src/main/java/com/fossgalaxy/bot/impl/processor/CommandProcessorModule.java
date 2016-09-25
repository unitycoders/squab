package com.fossgalaxy.bot.impl.processor;

import com.fossgalaxy.bot.api.module.ModuleCatalogue;
import com.fossgalaxy.bot.backend.Dispatcher;
import com.google.inject.AbstractModule;

/**
 * Created by webpigeon on 25/09/16.
 */
public class CommandProcessorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ModuleCatalogue.class).toInstance(new ModuleCatalogue());
        bind(CommandParser.class);
        bind(Dispatcher.class);
    }
}
