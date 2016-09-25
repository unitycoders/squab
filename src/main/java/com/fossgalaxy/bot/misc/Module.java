package com.fossgalaxy.bot.misc;

import java.util.Map;

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
     * @param map
     * @param object
     * @return
     */
    String execute(Map map, Object object);

    /**
     * Get the human readable name for this module.
     *
     * @return the name of the module
     */
    String getName();
}
