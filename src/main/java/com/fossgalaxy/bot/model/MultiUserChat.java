package com.fossgalaxy.bot.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by webpigeon on 25/09/16.
 */
public class MultiUserChat {
    public final String network;
    public final String name;
    public final Set<String> users;

    public MultiUserChat(String network, String name) {
        this.network = network;
        this.name = name;
        this.users = new HashSet<>();
    }

    public void addUser(String nick) {
        users.add(nick);
    }

    public void removeUser(String nick) {
        users.remove(nick);
    }

}
