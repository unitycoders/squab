package com.fossgalaxy.bot.api;

/**
 * A collection of logically related Actions.
 *
 * @see Action
 */
public interface Module {

    /**
     * Initialise a module so that it is ready to receive requests.
     */
    default void init() {}

    /**
     * Process a request from a user and generates a response.
     *
     * @param ctx
     * @param request
     * @return
     */
    Response execute(Context ctx, Request request);

    /**
     * Get the human readable name for this module.
     *
     * @return the name of the module
     */
    String getName();
}
