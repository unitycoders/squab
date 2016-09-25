package com.fossgalaxy.bot.api;

/**
 * A single `unit of work' for the bot.
 *
 * This represents a task that the bot should aim to complete.
 */
@FunctionalInterface
public interface Action {

    Response invoke(Context ctx, Request request);

}
