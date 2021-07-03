package com.java.grpc.hello.calculator.server;

import com.java.grpc.hello.streaming.bidirectional.client.ChatServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class CalculatorServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Starting Calculator Server");

        Server server = ServerBuilder.forPort(50051)
                .addService(new CalculatorServiceImpl())
                .build();
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("Calculator Server shutdown request");
            server.shutdown();
            System.out.println("Successfully stopped the Calculator Server");
        }));

        server.awaitTermination();


    }

}
