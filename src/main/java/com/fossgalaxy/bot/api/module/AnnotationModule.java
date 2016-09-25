package com.fossgalaxy.bot.api.module;

import com.fossgalaxy.bot.api.*;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * A module which uses Annotations discover actions and reflection to execute actions.
 */
public class AnnotationModule implements Module {
    private final String name;
    private final Map<String, AnnotationNode> methods;

    public AnnotationModule(String name) {
        this.name = name;
        this.methods = new HashMap<>();
    }

    public void init() {
        bind();
    }

    @Override
    public Response execute(Context context, Request request) {
        String action = request.getAction();

        AnnotationNode node = methods.get(action);
        if (node == null){
            return null;
        }

        return node.invoke(context, request);
    }

    @Override
    public String getName() {
        return name;
    }

    protected void bind(){
        Method[] methodList = MethodUtils.getMethodsWithAnnotation(getClass(), Command.class);
        if (methodList.length == 0) {
            System.out.println("nope, none of those pesky annotations here!");
            return;
        }

        for (Method method : methodList) {
            Command command = method.getAnnotation(Command.class);
            methods.put(command.value(), new AnnotationNode(this, method));
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
