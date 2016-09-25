package com.fossgalaxy.bot.misc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * An action executed using reflection.
 */
class AnnotationNode implements Action {
    private final Module parent;
    private final Method method;

    AnnotationNode(Module parent, Method method) {
        this.parent = parent;
        this.method = method;
    }

    @Override
    public String invoke(Map context, Object request) {
        try {
            return (String)method.invoke(parent, context, request);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
