package com.fossgalaxy.bot.backend.netty.irc;

import java.util.List;

/**
 * Created by webpigeon on 25/09/16.
 */
public class IRCEvent {
    public final String prefix;
    public final String command;
    private final List<String> args;

    public IRCEvent(String prefix, String command, List<String> args) {
        this.prefix = prefix;
        this.command = command;
        this.args = args;
    }

    public String toString(){
        return String.format("[%s]: %s(%s)", prefix, command, args);
    }

    public String getArg(int i) {
        return args.get(i);
    }
}
