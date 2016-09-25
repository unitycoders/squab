package com.fossgalaxy.bot.backend;

import com.fossgalaxy.bot.api.Context;
import com.fossgalaxy.bot.api.InvalidRequestException;
import com.fossgalaxy.bot.api.Response;
import com.fossgalaxy.bot.impl.DefaultContext;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by webpigeon on 25/09/16.
 */
public class TelnetBackend implements Backend {
    private final Dispatcher dispatcher;
    private final ExecutorService pool;

    public TelnetBackend(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.pool = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(1337);

            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                Scanner scanner = new Scanner(socket.getInputStream());
                PrintStream output = new PrintStream(socket.getOutputStream());
                pool.submit(new TelnetWorker(output, scanner, dispatcher));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
