package com.java.grpc.hello.streaming.bidirectional.client;

import com.proto.streaming.ChatRequest;
import com.proto.streaming.ChatResponse;
import com.proto.streaming.ChatStreamingGrpc;
import com.proto.unary.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ChatClient {

    ManagedChannel channel;


    private void run() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        doChatStreaming(channel);

        System.out.println("Closed the connection with the Chat server");
        channel.shutdown();

    }

    private void doChatStreaming(ManagedChannel channel) {

        ChatStreamingGrpc.ChatStreamingStub asyncClient = ChatStreamingGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<ChatRequest> chatRequestObserver = asyncClient.chat(new StreamObserver<ChatResponse>() {
            @Override
            public void onNext(ChatResponse value) {
                System.out.println("Response from Server: "
                        + "\n    Greeting: " + value.getGreeting()
                        + "\n    Received: " + value.getReceivedRequest()
                        + "\n    Message: " + value.getMessage()
                        + "\n    Value: " + value);

            }

            @Override
            public void onError(Throwable t) {
                latch.countDown();

            }

            @Override
            public void onCompleted() {
                System.out.println("Chat is closed");
                latch.countDown();
            }
        });

        Arrays.asList("Richie", "Basti", "Ares", "DeeDee").forEach(
                name -> chatRequestObserver.onNext(
                        ChatRequest.newBuilder()
                                .setGreeting(Greeting.newBuilder().setFirstName(name).build())
                                .setMessage(name + " is sending regards !")
                                .build()
                )
        );

        chatRequestObserver.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("Starting the Chat Client");

        ChatClient chatClient = new ChatClient();
        chatClient.run();
    }
}
