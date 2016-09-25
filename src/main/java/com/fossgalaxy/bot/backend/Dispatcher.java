package com.fossgalaxy.bot.backend;

import com.fossgalaxy.bot.api.*;
import com.fossgalaxy.bot.api.module.ModuleCatalogue;
import com.fossgalaxy.bot.impl.processor.CommandParser;
import com.google.inject.Inject;

/**
 * Created by webpigeon on 25/09/16.
 */
public class Dispatcher {
    private final CommandParser parser;
    private final ModuleCatalogue catalogue;

    @Inject
    public Dispatcher(CommandParser parser, ModuleCatalogue catalogue) {
        this.parser = parser;
        this.catalogue = catalogue;
    }

    public synchronized Response dispatch(Context ctx, String input) {
        Request request = parser.parse(input);

        Module module = catalogue.get(request.getModule());
        if (module == null) {
            throw new InvalidRequestException(request.getModule());
        }

        return module.execute(ctx, request);
    }

}
