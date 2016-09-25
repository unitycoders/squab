package com.fossgalaxy.bot.misc;

import java.util.Map;

/**
 * Created by webpigeon on 25/09/16.
 */
public interface Module {

    default void init() {}

    String execute(Map map, Object object);
}
