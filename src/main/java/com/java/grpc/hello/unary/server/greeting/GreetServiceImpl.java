package com.java.grpc.hello.unary.server.greeting;

import com.proto.unary.GreetRequest;
import com.proto.unary.GreetResponse;
import com.proto.unary.GreetServiceGrpc.GreetServiceImplBase;
import com.proto.unary.Greeting;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceImplBase {

    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        //extract the needed fields from the request
        Greeting greeting = request.getGreeting();
        String firstName = greeting.getFirstName();

        //create the response
        String result = "Hello " + firstName;
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(result)
                .build();

        //send the response
        responseObserver.onNext(response);

        //complete the RPC call
        responseObserver.onCompleted();

    }
}
