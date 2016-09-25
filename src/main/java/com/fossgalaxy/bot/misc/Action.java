package com.fossgalaxy.bot.misc;

import java.util.Map;

/**
 * A single `unit of work' for the bot.
 *
 * This represents a task that the bot should aim to complete.
 */
@FunctionalInterface
public interface Action {

    String invoke(Map context, Object request);

}
