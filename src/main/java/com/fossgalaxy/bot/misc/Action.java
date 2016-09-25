package com.fossgalaxy.bot.misc;

import java.util.Map;

/**
 * Created by webpigeon on 25/09/16.
 */
@FunctionalInterface
public interface Action {

    String invoke(Map context, Object request);

}
