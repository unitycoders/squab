package com.fossgalaxy.bot.misc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by webpigeon on 25/09/16.
 */
public class AnnotationNode implements Action {
    private Module parent;
    private Method method;

    public AnnotationNode(Module parent, Method method) {
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
