package com.waiting.system;

import com.waiting.system.controller.CommandProcessor;
import com.waiting.system.service.RestaurantManager;
import service.cloud.Console;

public class Main {
    public static void main(String[] args) {
        RestaurantManager manager = new RestaurantManager();
        CommandProcessor commandProcessor = new CommandProcessor(manager);

        while (true) {
            Console.print("> ");
            String input = Console.read();

            if (input.equalsIgnoreCase("exit")) break;

            commandProcessor.processCommand(input);
        }
    }
}