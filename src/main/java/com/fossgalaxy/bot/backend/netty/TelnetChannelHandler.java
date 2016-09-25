package com.fossgalaxy.bot.backend.netty;

import com.fossgalaxy.bot.api.Context;
import com.fossgalaxy.bot.api.InvalidRequestException;
import com.fossgalaxy.bot.api.Response;
import com.fossgalaxy.bot.backend.Dispatcher;
import com.fossgalaxy.bot.impl.DefaultContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by webpigeon on 25/09/16.
 */
public class TelnetChannelHandler extends ChannelInboundHandlerAdapter {
    private final Dispatcher dispatcher;

    public TelnetChannelHandler(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            Context context = new DefaultContext();
            context.put(Context.USER, ctx.name());

            Response r = dispatcher.dispatch(context, msg.toString());
            ctx.write(r.getOutput()+"\r\n");
            ctx.flush();
        } catch (InvalidRequestException ex) {
            ex.printStackTrace();
            ctx.write("error: "+ex.getMessage()+"\r\n");
            ctx.flush();
        }
    }
}
