package com.java.grpc.hello.calculator.server;

import com.proto.streaming.CalculatorServiceGrpc;
import com.proto.streaming.SquareRootRequest;
import com.proto.streaming.SquareRootResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    @Override
    public void squareRoot(SquareRootRequest request, StreamObserver<SquareRootResponse> responseObserver) {

        Integer number = request.getNumber();
        if (number >= 0) {
            double root = Math.sqrt(number);
            responseObserver.onNext(
                    SquareRootResponse.newBuilder()
                            .setRoot(root)
                            .build()
            );

            responseObserver.onCompleted();
        } else {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT
                            .withDescription("The sent number is not valid")
                            .augmentDescription("You can write additional error lines using augmentDescription()")
                            .asRuntimeException()
            );
        }

        super.squareRoot(request, responseObserver);
    }
}
