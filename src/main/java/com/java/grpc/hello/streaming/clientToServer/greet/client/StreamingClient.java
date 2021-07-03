package com.java.grpc.hello.streaming.clientToServer.greet.client;

import com.java.grpc.hello.streaming.clientToServer.greet.server.LongGreetingServiceImpl;
import com.proto.streaming.ClientStreamingGrpc;
import com.proto.streaming.LongGreetingRequest;
import com.proto.streaming.LongGreetingResponse;
import com.proto.unary.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class StreamingClient {

    ManagedChannel channel;

    private void run() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        doClientStreamingCall(channel);

        System.out.println("Closed the connection with the server");
        channel.shutdown();

    }

    private void doClientStreamingCall(ManagedChannel channel) {
        System.out.println("Creating ASYNC Stub");

        ClientStreamingGrpc.ClientStreamingStub asynchronousClient = ClientStreamingGrpc.newStub(channel);

        /**
         * CountDownLatch is a concurrency construct that allows one or more threads to wait for a given set of operations to complete.
         * A CountDownLatch is initialized with a given count.
         * This count is decremented by calls to the countDown() method. ...
         * Calling await() blocks the thread until the count reaches zero.
         */
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<LongGreetingRequest> requestObserver = asynchronousClient.longGreet(new StreamObserver<LongGreetingResponse>() {
            @Override
            public void onNext(LongGreetingResponse value) {
                //when we get a response from the server
                // onNext() will be called only once

                System.out.println("Received Response from the Server");
                System.out.println(value.getResult());

            }

            @Override
            public void onError(Throwable t) {
                //we get an error from the server
                System.out.println("Error on the StreamingClient");

            }

            @Override
            public void onCompleted() {
                //the server is done sending us data
                //onCompleted() will be called right after onNext()
                System.out.println("Server has COMPLETED sending data");
                latch.countDown();

            }
        });

        System.out.println("Sending streaming message #1");
        requestObserver.onNext(
                LongGreetingRequest.newBuilder()
                        .setGreeting(Greeting.newBuilder().setFirstName("Richie").build())
                        .build()
        );

        System.out.println("Sending streaming message #2");
        requestObserver.onNext(
                LongGreetingRequest.newBuilder()
                        .setGreeting(Greeting.newBuilder().setFirstName("Basti").build())
                        .build()
        );

        System.out.println("Sending streaming message #3");
        requestObserver.onNext(
                LongGreetingRequest.newBuilder()
                        .setGreeting(Greeting.newBuilder().setFirstName("Ares").build())
                        .build()
        );

        //we tell the server that the client is done sending data
        requestObserver.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {

        System.out.println("Started the client to stream long greetings");

        StreamingClient main = new StreamingClient();
        main.run();






    }
}
