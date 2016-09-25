package com.fossgalaxy.bot.backend.netty.irc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webpigeon on 25/09/16.
 */
public class IRCChannelHandler extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) throws Exception {
        list.add(split(s));
    }


    public IRCEvent split(String msg) {
        List<String> parts = new ArrayList<>();

        int start = 0;
        boolean lastParam = false;
        boolean prefix = false;

        //if there is a server paramter, we start at 1
        if (msg.charAt(0) == ':'){
            prefix = true;
            start = 1;
        }

        StringBuffer buff = new StringBuffer();
        for (int i=start; i<msg.length(); i++) {
            char curr = msg.charAt(i);
            if (curr == ' ' && !lastParam){
                if (buff.length() != 0) {
                    parts.add(buff.toString());
                    buff = new StringBuffer();
                }
            } else if (curr == ':' && buff.length() == 0 && !lastParam) {
                lastParam = true;
            } else {
                buff.append(curr);
            }
        }

        parts.add(buff.toString());

        if (prefix) {
            return new IRCEvent(parts.get(0), parts.get(1), parts.subList(2, parts.size()));
        } else {
            return new IRCEvent(null, parts.get(0), parts.subList(1, parts.size()));
        }
    }

}
