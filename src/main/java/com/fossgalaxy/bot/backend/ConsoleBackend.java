package com.fossgalaxy.bot.backend;

import com.fossgalaxy.bot.api.Context;
import com.fossgalaxy.bot.api.InvalidRequestException;
import com.fossgalaxy.bot.api.Response;
import com.fossgalaxy.bot.impl.DefaultContext;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by webpigeon on 25/09/16.
 */
public class ConsoleBackend implements Runnable {
    private final Scanner scanner;
    private final PrintStream output;
    private final Dispatcher dispatcher;

    public ConsoleBackend( Dispatcher dispatcher ) {
        this.scanner = new Scanner(System.in);
        this.output = System.out;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {

        output.print("bot> ");
        while(scanner.hasNextLine()) {
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
