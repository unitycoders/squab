package com.fossgalaxy.bot.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by webpigeon on 25/09/16.
 */
public class MUCStorage {
    private final Map<String, MultiUserChat> chat;

    public MUCStorage(){
        this.chat = new HashMap<>();
    }

    public MultiUserChat getChat(String network, String room) {
        String key = String.format("%s+%s", network, room);

        MultiUserChat muc = chat.get(key);
        if (muc == null) {
            muc = new MultiUserChat(network, room);
            chat.put(key, muc);
        }

        return muc;
    }

    public Set<String> getKeys() {
        return chat.keySet();
    }

    public void removeFromAll(String nick) {
        for (MultiUserChat chatRoom : chat.values()) {
            chatRoom.removeUser(nick);
        }
    }

}
