package com.java.grpc.hello.client;

import com.proto.test.TestServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {

    public static void main(String[] args) {
        System.out.println("This is the gRPC client");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() //this will disable SSL. Only use in development
                .build();

        System.out.println("Creating Stub");
        //creating a SYNCHRONOUS client
        TestServiceGrpc.TestServiceBlockingStub syncClient = TestServiceGrpc.newBlockingStub(channel);

        //creating an ASYNCHRONOUS client
        //TestServiceGrpc.TestServiceFutureStub asyncClient = TestServiceGrpc.newFutureStub(channel);

        // do some stuff

        System.out.println("Shutting down channel");
        channel.shutdown();


    }

}
