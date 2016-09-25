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

/**
 * Created by webpigeon on 25/09/16.
 */
public class TelnetWorker implements Runnable {
    private final PrintStream output;
    private final Scanner scanner;
    private final Dispatcher dispatcher;

    public TelnetWorker(PrintStream output, Scanner scanner, Dispatcher dispatcher) {
        this.output = output;
        this.scanner = scanner;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        output.print("bot> ");
           while (scanner.hasNextLine()) {
               try {
                    Context context = new DefaultContext();
                    context.put(Context.USER, "consoleUser");

                    String input = scanner.nextLine();
                    Response response = dispatcher.dispatch(context, input);
                    output.println(response.getOutput());
                } catch (InvalidRequestException ex) {
                    output.println(String.format("error: %s", ex.getMessage()));
                }
                output.print("bot> ");
            }

    }
}
