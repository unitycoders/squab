package com.fossgalaxy.bot.misc;

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
    public String getAction() {
        return action;
    }
}
