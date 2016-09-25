package com.fossgalaxy.bot.impl;

import com.fossgalaxy.bot.api.Response;

/**
 * Respond to a request with a string
 */
public class TemplateResponse implements Response {
    private final String template;
    private final Object[] args;

    public TemplateResponse(String template, Object ... args) {
        this.template = template;
        this.args = args;
    }

    @Override
    public String getOutput() {
        return String.format(template, args);
    }

    public String toString() {
        return String.format("(TemplateResponse,\"%s\")", getOutput());
    }
}
