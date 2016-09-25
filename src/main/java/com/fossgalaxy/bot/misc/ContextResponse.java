package com.fossgalaxy.bot.misc;

import org.apache.commons.lang3.text.StrSubstitutor;

/**
 * Created by webpigeon on 25/09/16.
 */
public class ContextResponse implements Response {
    private final String template;
    private final Context context;

    public ContextResponse(String template, Context context){
        this.template = template;
        this.context = context;
    }

    @Override
    public String getOutput() {
        return StrSubstitutor.replace(template, context);
    }

    public String toString() {
        return String.format("(ContextResponse,\"%s\")", getOutput());
    }
}
