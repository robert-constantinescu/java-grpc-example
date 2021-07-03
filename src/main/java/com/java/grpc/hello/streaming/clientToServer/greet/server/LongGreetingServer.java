package com.java.grpc.hello.streaming.clientToServer.greet.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class LongGreetingServer {

    public static void main(String[] args) throws InterruptedException, IOException {

        System.out.println("Hello Client Streaming Server");

        Server server = ServerBuilder.forPort(50051)
                .addService(new LongGreetingServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("Client Streaming Server shutdown request");
            server.shutdown();
            System.out.println("Successfully stopped the Client Streaming Server");
        }));

        server.awaitTermination();
    }
}
