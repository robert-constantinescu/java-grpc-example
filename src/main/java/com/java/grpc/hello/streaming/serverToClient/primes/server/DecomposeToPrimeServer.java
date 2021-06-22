package com.java.grpc.hello.streaming.serverToClient.primes.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class DecomposeToPrimeServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting server that decompose number to it's primes factors");

        Server server = ServerBuilder.forPort(50051)
                .addService(new DecomposeToPrimesServiceImpl())
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
