package com.fossgalaxy.bot.api;

/**
 * Indicates the user did not make a valid request.
 *
 */
public class InvalidRequestException extends RuntimeException {
    private final String module;
    private final String action;

    public InvalidRequestException(String module, String action) {
        super(String.format("action %s not defined module %s", action, module));
        this.module = module;
        this.action = action;
    }

    public InvalidRequestException(String module) {
        super(String.format("module %s is not a valid module", module));
        this.module = module;
        this.action = null;
    }

}
