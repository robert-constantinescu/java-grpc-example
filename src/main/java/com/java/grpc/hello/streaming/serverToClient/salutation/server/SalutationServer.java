package com.java.grpc.hello.streaming.serverToClient.salutation.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class SalutationServer {

    public static void main(String[] args) throws InterruptedException, IOException {

        System.out.println("Hello Streaming Server");

        Server server = ServerBuilder.forPort(50051)
                .addService(new SalutationServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("Streaming Server shutdown request");
            server.shutdown();
            System.out.println("Successfully stopped the Streaming Server");
        }));

        server.awaitTermination();
    }
}
