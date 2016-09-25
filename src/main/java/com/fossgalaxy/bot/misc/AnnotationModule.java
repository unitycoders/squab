package com.fossgalaxy.bot.misc;

import com.fossgalaxy.bot.Command;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by webpigeon on 25/09/16.
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
    public String execute(Map context, Object request) {
        String action = "hello";

        AnnotationNode node = methods.get(action);
        if (node == null){
            return null;
        }

        return node.invoke(context, request);
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
