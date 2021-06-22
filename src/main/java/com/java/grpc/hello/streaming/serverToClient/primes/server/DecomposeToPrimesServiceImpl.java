package com.java.grpc.hello.streaming.serverToClient.primes.server;


import io.grpc.stub.StreamObserver;
import streaming.DecomposeToPrimesGrpc;
import streaming.PrimeNumberDecomposition;
import streaming.PrimeNumberDecomposition.PrimeNumberResponse;
import streaming.PrimeNumberDecomposition.PrimeNumber;

public class DecomposeToPrimesServiceImpl extends DecomposeToPrimesGrpc.DecomposeToPrimesImplBase {

    @Override
    public void decompose(PrimeNumberDecomposition.PrimeNumberRequest request, StreamObserver<PrimeNumberDecomposition.PrimeNumberResponse> responseObserver) {
        long number = request.getNumber();
        int k = 2;

        PrimeNumberResponse.Builder responseBuilder = PrimeNumberDecomposition.PrimeNumberResponse.newBuilder();
        PrimeNumber.Builder primeNumberBuilder = PrimeNumber.newBuilder();

        while(number > 1) {

            if (number % k == 0 ) {

                number = number / k;
                System.out.println("Prime Factor is: " + k);
                responseBuilder
                        .setPrimeNumber(primeNumberBuilder.setPrimeNumber(k));

                responseObserver.onNext(responseBuilder.build());
            } else {
                k = k + 1;
            }

        }

        responseObserver.onCompleted();

    }
}
