package com.fossgalaxy.bot.api;

import java.util.Map;

/**
 * Created by webpigeon on 25/09/16.
 */
public interface Context extends Map<String, Object> {
    String USER = "user";
    String TARGET = "target";
    String REPLY_TO = "reply";
}
