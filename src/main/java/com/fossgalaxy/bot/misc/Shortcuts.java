package com.fossgalaxy.bot.misc;

/**
 * Created by webpigeon on 25/09/16.
 */
public class Shortcuts {
    private static final Response RESPOND_SUCCESS = new TextResponse("the operation succeeded.");

    public static Response respondWith(String template, Context ctx) {
        return new ContextResponse(template, ctx);
    }

    public static Response respond(String message) {
        return new TextResponse(message);
    }

    public static Response respondFormatted(String format, Object ... args) {
        return new TemplateResponse(format, args);
    }

    public static Response respondError(String errorMessage) {
        return respondFormatted("error: %s", errorMessage);
    }

    public static Response respondError(String errorMessage, Throwable cause) {
        return respondFormatted("error: %s (caused by %s)", errorMessage, cause.getClass());
    }

    public static Response respondSuccess() {
        return RESPOND_SUCCESS;
    }
}
