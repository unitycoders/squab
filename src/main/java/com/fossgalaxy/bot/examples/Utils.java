package com.fossgalaxy.bot.examples;

import java.util.List;
import java.util.Random;

/**
 * Created by webpigeon on 25/09/16.
 */
public class Utils {
    private static final Random random = new Random();

    public static <T> T chooseOne(T[] elements) {
        int choice = random.nextInt(elements.length);
        return elements[choice];
    }

    public static <T> T chooseOne(List<T> elements) {
        int choice = random.nextInt(elements.size());
        return elements.get(choice);
    }
}
