package com.java.grpc.hello.streaming.clientToServer.greet.server;

import com.proto.streaming.ClientStreamingGrpc;
import com.proto.streaming.LongGreetingRequest;
import com.proto.streaming.LongGreetingResponse;
import io.grpc.stub.StreamObserver;

public class LongGreetingServiceImpl extends ClientStreamingGrpc.ClientStreamingImplBase {

    @Override
    public StreamObserver<LongGreetingRequest> longGreet(StreamObserver<LongGreetingResponse> responseObserver) {
        System.out.println("Long Greeting Service Impl");
        StreamObserver<LongGreetingRequest> requestObserver = new StreamObserver<LongGreetingRequest>() {

            String result = "";

            @Override
            public void onNext(LongGreetingRequest value) {
                System.out.println("LongGreetingService onNext()");
                //client sends a message
                result += "> Hello " + value.getGreeting().getFirstName() +"! <";
            }

            @Override
            public void onError(Throwable t) {
                //client sends an error
            }

            @Override
            public void onCompleted() {
                //client is done
                //here is when we want to return a response(responseObserver)
                System.out.println("LongGreetingService onCompleted()");

                responseObserver.onNext(
                        LongGreetingResponse.newBuilder()
                                .setResult(result)
                                .build()
                );
                responseObserver.onCompleted();
            }
        };

        return requestObserver;
    }
}
