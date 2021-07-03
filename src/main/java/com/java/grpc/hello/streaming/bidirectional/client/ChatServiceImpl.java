package com.java.grpc.hello.streaming.bidirectional.client;

import com.proto.streaming.ChatRequest;
import com.proto.streaming.ChatResponse;
import com.proto.streaming.ChatStreamingGrpc;
import io.grpc.stub.StreamObserver;

public class ChatServiceImpl extends ChatStreamingGrpc.ChatStreamingImplBase {

    @Override
    public StreamObserver<ChatRequest> chat(StreamObserver<ChatResponse> responseObserver) {
        StreamObserver<ChatRequest> streamObserver = new StreamObserver<ChatRequest>() {
            @Override
            public void onNext(ChatRequest value) {
                System.out.println("Value received on Server: " + "\n  " + value);
                String result = "Hello " + value.getGreeting().getFirstName();
                ChatResponse chatResponse = ChatResponse.newBuilder()
                        .setMessage(result)
                        .setReceivedRequest(true)
                        .build();

                responseObserver.onNext(chatResponse);
            }

            @Override
            public void onError(Throwable t) {
                ChatResponse chatResponse = ChatResponse.newBuilder()
                        .setReceivedRequest(false)
                        .build();

                responseObserver.onNext(chatResponse);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };

        return streamObserver;
    }
}
