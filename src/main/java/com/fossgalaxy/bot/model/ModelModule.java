package com.fossgalaxy.bot.model;

import com.google.inject.AbstractModule;

/**
 * Created by webpigeon on 25/09/16.
 */
public class ModelModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MUCStorage.class).toInstance(new MUCStorage());
    }
}
