package com.java.grpc.hello.unary.client.sum;

import com.proto.unary.SumRequest;
import com.proto.unary.SumResponse;
import com.proto.unary.SumServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SumClient {


    public static void main(String[] args) {

        System.out.println("SumClient executing");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .build();

        System.out.println("Creating SumClient Stub");

        SumServiceGrpc.SumServiceBlockingStub sumClient = SumServiceGrpc.newBlockingStub(channel);

        SumRequest request = SumRequest.newBuilder()
                .setFirstInteger(5)
                .setSecondInteger(32)
                .build();

        SumResponse response = sumClient.sum(request);


        System.out.println("Request: \n" + request);

        System.out.println("Response: \n" +response);



    }
}
