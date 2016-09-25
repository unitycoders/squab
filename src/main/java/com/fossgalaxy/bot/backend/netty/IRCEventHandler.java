package com.fossgalaxy.bot.backend.netty;

import com.fossgalaxy.bot.api.Context;
import com.fossgalaxy.bot.api.InvalidRequestException;
import com.fossgalaxy.bot.api.Response;
import com.fossgalaxy.bot.backend.Dispatcher;
import com.fossgalaxy.bot.impl.DefaultContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by webpigeon on 25/09/16.
 */
public class IRCEventHandler extends SimpleChannelInboundHandler<IRCEvent> {
    private final Dispatcher dispatcher;
    private final EventDispatcher<IRCEvent> eventDispatcher;

    public IRCEventHandler(Dispatcher dispatcher, EventDispatcher<IRCEvent> eventDispatcher) {
        this.dispatcher = dispatcher;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IRCEvent s) throws Exception {

        System.out.println(s);
        eventDispatcher.fire(s.command, s);

        switch (s.command) {
            case "PRIVMSG":
                handleRequest(ctx, s);
                break;
            case "PING":
                ctx.writeAndFlush(String.format("PONG :%s\r\n", s.args.get(0)));
                break;
        }

    }

    private void handleRequest(ChannelHandlerContext channel, IRCEvent event) {
        assert "PRIVMSG".equals(event.command);

        UserMask mask = UserMask.parse(event.prefix);
        String target = event.args.get(0);

        Context ctx = new DefaultContext();
        ctx.put(Context.USER, mask.nick);
        ctx.put(Context.TARGET, target);

        if (target.charAt(0) == '#') {
            ctx.put(Context.REPLY_TO, target);
        } else {
            ctx.put(Context.REPLY_TO, mask.nick);
        }

        String request = event.args.get(1);

        //IRC Specific:
        ctx.put("irc.mask", mask);

        try {
            Response response = dispatcher.dispatch(ctx, request);
            channel.writeAndFlush(String.format("PRIVMSG %s :%s\r\n", ctx.get(Context.REPLY_TO), response.getOutput()));
        } catch (InvalidRequestException ex) {
            channel.writeAndFlush(String.format("PRIVMSG %s :error: %s\r\n", ctx.get(Context.USER), ex.getMessage()));
        }
    }

    public static String mask2Nick(String mask) {
        return mask.split("!")[0];
    }
}
