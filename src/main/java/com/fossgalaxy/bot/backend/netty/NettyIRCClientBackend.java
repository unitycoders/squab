package com.fossgalaxy.bot.backend.netty;

import com.fossgalaxy.bot.backend.Backend;
import com.fossgalaxy.bot.backend.Dispatcher;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by webpigeon on 25/09/16.
 */
public class NettyIRCClientBackend implements Backend {

    private final Dispatcher dispatcher;
    private final String hostname;
    private final int port;
    private final EventDispatcher<IRCEvent> eventDispatcher;

    private Channel channel;

    public NettyIRCClientBackend(String hostname, int port, EventDispatcher<IRCEvent> ed, Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.eventDispatcher = ed;
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (2)
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            //b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                    ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                    ch.pipeline().addLast(new IRCChannelHandler());
                    ch.pipeline().addLast(new IRCEventHandler(dispatcher, eventDispatcher));
                }
            });

            ChannelFuture f = b.connect(hostname, port).sync();
            channel = f.channel();

            sendRaw(f.channel(), "NICK uc_squab2");
            sendRaw(f.channel(), "USER squab 0 unused :FOSSGalaxy Squab bot");
            sendRaw(f.channel(), "JOIN #fossgalaxy-bots");

            f.channel().closeFuture().sync();

        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void sendRaw(Channel c, String msg){
        c.writeAndFlush(msg+"\r\n");
    }

    @Override
    public void sendRaw(String input) {
        if (channel == null) {
            return;
        }
        sendRaw(channel, input);
    }

    @Override
    public void terminate() {

    }
}
