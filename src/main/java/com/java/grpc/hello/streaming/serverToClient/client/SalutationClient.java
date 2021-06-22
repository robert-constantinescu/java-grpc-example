package com.java.grpc.hello.streaming.serverToClient.client;

import com.proto.streaming.SalutationRequest;
import com.proto.streaming.SalutationServiceGrpc;
import com.proto.streaming.SalutationServiceGrpc.SalutationServiceBlockingStub;
import com.proto.unary.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SalutationClient {

    public static void main(String[] args) {
        System.out.println("This is the client for the Server Streaming");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        System.out.println("Creating ASYNCHRONOUS Stub");

        SalutationServiceBlockingStub salutationClient = SalutationServiceGrpc.newBlockingStub(channel);

        SalutationRequest request = SalutationRequest.newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Richie"))
                .build();

        salutationClient.salute(request)
                .forEachRemaining(
                        salutationResponse -> {
                            System.out.println(salutationResponse.getResult());
                        }
                );

        System.out.println("Closed the connection with the streaming server");
        channel.shutdown();

    }
}
