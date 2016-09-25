package com.fossgalaxy.bot.backend.netty;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by webpigeon on 25/09/16.
 */
@Singleton
public class EventDispatcher<T> {
    private final Map<String, List<Consumer<T>>> callbacks;

    public EventDispatcher() {
        this.callbacks = new HashMap<>();
    }

    public void register(String event, Consumer<T> consumer) {
        assert consumer != null;
        assert event != null;

        List<Consumer<T>> consumers = callbacks.get(event);
        if (consumers == null) {
            consumers = new ArrayList<>();
            callbacks.put(event, consumers);
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
