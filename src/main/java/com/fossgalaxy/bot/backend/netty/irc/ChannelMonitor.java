package com.fossgalaxy.bot.backend.netty.irc;

import com.fossgalaxy.bot.backend.netty.EventDispatcher;
import com.fossgalaxy.bot.model.MUCStorage;
import com.fossgalaxy.bot.model.MultiUserChat;
import com.google.inject.Inject;

import java.util.Arrays;

/**
 * Created by webpigeon on 25/09/16.
 */
public class ChannelMonitor {
    private MUCStorage store;

    @Inject
    public ChannelMonitor(MUCStorage store) {
        this.store = store;
    }

    @Inject
    public void bind(EventDispatcher<IRCEvent> dispatcher) {
        dispatcher.register("JOIN", this::onJoin);
        dispatcher.register("PART", this::onPart);
        dispatcher.register("QUIT", this::onQuit);
        dispatcher.register("353", this::onChannelList);
    }

    public void onJoin(IRCEvent joinEvent) {
        MultiUserChat muc = store.getChat("irc", joinEvent.getArg(0));
        String nick = IRCEventHandler.mask2Nick(joinEvent.prefix);
        muc.addUser(nick);
    }

    public void onPart(IRCEvent partEvent) {
        MultiUserChat muc = store.getChat("irc", partEvent.getArg(0));
        String nick = IRCEventHandler.mask2Nick(partEvent.prefix);
        muc.removeUser(nick);
    }

    public void onQuit(IRCEvent quitEvent) {
        String nick = IRCEventHandler.mask2Nick(quitEvent.prefix);
        store.removeFromAll(nick);
    }

    public void onChannelList(IRCEvent channelListEvent) {
        String[] nicks = channelListEvent.getArg(3).split(" ");
        MultiUserChat room = store.getChat("irc", channelListEvent.getArg(2));
        for (String nick : nicks) {
            room.addUser(nick);
        }
    }
}
