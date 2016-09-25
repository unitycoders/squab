package com.fossgalaxy.bot.impl;

import com.fossgalaxy.bot.api.Response;

/**
 * Respond to a request with a string
 */
public class TextResponse implements Response {
    private final String response;

    public TextResponse(String response) {
        this.response = response;
    }

    @Override
    public String getOutput() {
        return response;
    }

    public String toString() {
        return String.format("(TextResponse,\"%s\")", getOutput());
    }
}
