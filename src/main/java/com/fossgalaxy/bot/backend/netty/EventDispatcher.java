package com.fossgalaxy.bot.backend.netty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by webpigeon on 25/09/16.
 */
public class EventDispatcher<T> {
    private Map<String, List<Consumer<T>>> callbacks;

    public EventDispatcher() {
        this.callbacks = new HashMap<>();
    }

    public void register(String event, Consumer<T> consumer) {
        assert consumer != null;
        assert event != null;

        List<Consumer<T>> consumers = callbacks.get(event);
        if (consumers == null) {
            consumers = new ArrayList<>();
        }
        consumers.add(consumer);
    }

    public void fire(String event, T data) {
        List<Consumer<T>> consumers = callbacks.get(event);
        if (consumers == null) {
            return;
        }

        for (Consumer<T> consumer : consumers) {
            consumer.accept(data);
        }
    }

}
