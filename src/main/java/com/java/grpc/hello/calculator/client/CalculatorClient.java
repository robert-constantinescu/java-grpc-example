package com.java.grpc.hello.calculator.client;

import com.proto.streaming.Calculator;
import com.proto.streaming.CalculatorServiceGrpc;
import com.proto.streaming.SquareRootRequest;
import com.proto.streaming.SquareRootResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {

    ManagedChannel channel;

    public void run() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        doSquareRoot(channel);

        System.out.println("Closed the connection with the Calculator server");
        channel.shutdown();
    }

    private void doSquareRoot(ManagedChannel channel) {

        CalculatorServiceGrpc.CalculatorServiceBlockingStub blockingStub = CalculatorServiceGrpc.newBlockingStub(channel);

        int number = -4;
        try {
            SquareRootResponse squareRootResponse = blockingStub.squareRoot(
                    SquareRootRequest.newBuilder()
                            .setNumber(number)
                            .build()
            );

            System.out.println("squareRootResponse is => " + squareRootResponse);

        } catch (Exception e ) {
            System.out.println("Server returned an exception");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("Starting CalculatorClient");
        CalculatorClient client = new CalculatorClient();

        client.run();
    }
}
