package com.java.grpc.hello.streaming.serverToClient.server;


import com.proto.streaming.SalutationRequest;
import com.proto.streaming.SalutationResponse;
import com.proto.streaming.SalutationServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class SalutationServiceImpl extends SalutationServiceGrpc.SalutationServiceImplBase {

    @Override
    public void salute(SalutationRequest request, StreamObserver<SalutationResponse> responseObserver) {
        String firstName = request.getGreeting().getFirstName();

        for (int i = 0; i < 10; i++) {
            String result = "Hello " + firstName + ", response no: " + i;

            SalutationResponse response = SalutationResponse.newBuilder()
                    .setResult(result)
                    .build();

            responseObserver.onNext(response);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        responseObserver.onCompleted();
    }
}
