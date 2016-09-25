package com.fossgalaxy.bot.impl;

import com.fossgalaxy.bot.api.Request;

/**
 * Created by webpigeon on 25/09/16.
 */
public class DefaultRequest implements Request {

    private final String module;
    private final String action;

    public DefaultRequest(String module, String action) {
        this.module = module;
        this.action = action;
    }

    @Override
    public String getModule() {
        return module;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public String getArgument(int i) {
        return null;
    }
}
