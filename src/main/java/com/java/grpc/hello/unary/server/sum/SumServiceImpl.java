package com.java.grpc.hello.unary.server.sum;

import com.proto.unary.SumRequest;
import com.proto.unary.SumResponse;
import com.proto.unary.SumServiceGrpc.SumServiceImplBase;
import io.grpc.stub.StreamObserver;

public class SumServiceImpl extends SumServiceImplBase {

    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {
        //Extract the integers in separate variables
        int m1 = request.getFirstInteger();
        int m2 = request.getSecondInteger();

        //process the request, manipulate the data, do some stuff
        int sum = m1 + m2;

        //create the response
        SumResponse response = SumResponse.newBuilder()
                .setSum(sum)
                .build();

        //send the response
        responseObserver.onNext(response);

        //complete the RPC call
        responseObserver.onCompleted();
    }
}
