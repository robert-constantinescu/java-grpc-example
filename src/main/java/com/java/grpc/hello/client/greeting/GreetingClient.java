package com.java.grpc.hello.client.greeting;

import com.proto.test.TestServiceGrpc;
import com.proto.unary.GreetRequest;
import com.proto.unary.GreetResponse;
import com.proto.unary.GreetServiceGrpc;
import com.proto.unary.Greeting;
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
//        TestServiceGrpc.TestServiceBlockingStub syncClient = TestServiceGrpc.newBlockingStub(channel);

        //created a greet service client(synchronous)
        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        //created a protocol buffer greeting message
        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Richie")
                .setFirstName("Ares")
                .build();
        //created a protocol buffer GreetRequest
        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        // call the RPC and get back a GreetResponse (
        GreetResponse response = greetClient.greet(greetRequest);

        // do some stuff
        System.out.println(response);

        //creating an ASYNCHRONOUS client
        //TestServiceGrpc.TestServiceFutureStub asyncClient = TestServiceGrpc.newFutureStub(channel);


        System.out.println("Shutting down channel");
        channel.shutdown();


    }

}
