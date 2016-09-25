package com.fossgalaxy.bot.impl.processor;

import com.fossgalaxy.bot.api.Request;

import java.util.Arrays;

/**
 * Created by webpigeon on 25/09/16.
 */
 class BasicRequest implements Request {
    private final String[] tokens;

    public BasicRequest(String[] tokens) {
        this.tokens = tokens;
    }

    @Override
    public String getModule() {
        if (tokens.length < 1) {
            return null;
        }

        return tokens[0];
    }

    @Override
    public String getAction() {
        if (tokens.length < 2) {
            return DEFAULT_ACTION;
        }

        return tokens[1];
    }

    public String toString() {
        return Arrays.toString(tokens);
    }
}
