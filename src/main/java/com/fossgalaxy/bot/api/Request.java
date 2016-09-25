package com.fossgalaxy.bot.api;

/**
 * Created by webpigeon on 25/09/16.
 */
public interface Request {
    String DEFAULT_ACTION = "default";

    String getModule();
    String getAction();

    String getArgument(int i);
}
