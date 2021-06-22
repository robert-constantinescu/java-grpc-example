package com.java.grpc.hello.streaming.serverToClient.primes.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import streaming.DecomposeToPrimesGrpc;
import streaming.PrimeNumberDecomposition.PrimeNumberRequest;

public class PrimeClient {

    public static void main(String[] args) {

        System.out.println("Started the client for decompose to prime server");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        System.out.println("Creating SYNCRHONOUS Stub");

        DecomposeToPrimesGrpc.DecomposeToPrimesBlockingStub client = DecomposeToPrimesGrpc.newBlockingStub(channel);


        PrimeNumberRequest request = PrimeNumberRequest.newBuilder()
                .setNumber(120)
                .build();

        client.decompose(request)
                .forEachRemaining( response -> {
                    System.out.println("Received Prime factor is: " + response);
                });

        System.out.println("Closed the connection with the streaming server");
        channel.shutdown();

    }
}
