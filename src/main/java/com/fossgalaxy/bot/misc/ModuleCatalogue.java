package com.fossgalaxy.bot.misc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by webpigeon on 25/09/16.
 */
public class ModuleCatalogue {
    private final Map<String, Module> modules;

    public ModuleCatalogue() {
        this.modules = new HashMap<>();
    }

    public void register(String name, Module module) {
        assert name != null;
        assert module != null;

        modules.put(name, module);
    }

    public Module get(String name) {
        assert name != null;
        return modules.get(name);
    }
}