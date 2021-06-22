package com.java.grpc.hello.server.sum;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class SumServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("gRPC SumServer Started");

        Server server = ServerBuilder.forPort(50051)
                .addService(new SumServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("gRPC SumServer will STOP");
            server.shutdown();
            System.out.println("SumServer STOPPED");
        }));

        server.awaitTermination();

    }
}
