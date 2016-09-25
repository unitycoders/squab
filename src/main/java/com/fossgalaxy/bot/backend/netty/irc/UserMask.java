package com.fossgalaxy.bot.backend.netty.irc;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by webpigeon on 25/09/16.
 */
public class UserMask implements Serializable {
    public final String nick;
    public final String username;
    public final String host;

    public UserMask(String nick, String username, String host) {
        this.nick = nick;
        this.username = username;
        this.host = host;
    }

    private final static Pattern pattern = Pattern.compile("(\\S+)!(\\S+)@(\\S+)");
    public static UserMask parse(String mask) {
        Matcher m = pattern.matcher(mask);
        if (!m.matches()) {
            throw new RuntimeException("invalid host mask provided?!");
        }
        return new UserMask(m.group(1), m.group(2), m.group(3));
    }

}
