package com.fossgalaxy.bot.api.module;

import com.fossgalaxy.bot.api.Module;

import java.util.HashMap;
import java.util.Map;

/**
 * A collection of Modules which are currently loaded by the bot.
 *
 * @see Module
 */
public class ModuleCatalogue {
    private final Map<String, Module> modules;

    public ModuleCatalogue() {
        this.modules = new HashMap<>();
    }

    public void load(String className) {
        try {
            Class<?> moduleClazz = Class.forName(className);
            Module module = (Module) moduleClazz.newInstance();
            module.init();
            register(module.getName(), module);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
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