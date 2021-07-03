package com.java.grpc.hello.streaming.bidirectional.server;

import com.java.grpc.hello.streaming.bidirectional.client.ChatServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ChatServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting Chat Server");

        Server server = ServerBuilder.forPort(50051)
                .addService(new ChatServiceImpl())
                .build();
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("Chat Server shutdown request");
            server.shutdown();
            System.out.println("Successfully stopped the Chat Server");
        }));

        server.awaitTermination();
    }
}
