package SmartClass.server;

import com.proto.SmartClassroom.DrawRequest;
import com.proto.SmartClassroom.DrawResponse;
import com.proto.SmartClassroom.drawingMateGrpc;
import io.grpc.stub.StreamObserver;

public class StreamDrawImpl extends drawingMateGrpc.drawingMateImplBase {

      /*
    // client side streaming for service one method 2
     */



    @Override
    public StreamObserver<DrawRequest> StreamDrawUpdates(StreamObserver<DrawResponse> responseObserver) {
        // if this doesn't work, try responseStreamObserver, or something, it'll give you a prompt
        // concatonate ContentIdRequests
        StringBuilder SB = new StringBuilder(); // this will return StreamObserver
        // create new StreamObserver and implement three functions: onNext, onError, and onComplete
        return new StreamObserver<DrawRequest>() {
            @Override
            public void onNext(DrawRequest request) {
                SB.append("Hello"); // we'll most likely remove this line
                SB.append(request.getDraw());
                SB.append("!\n");
            }

            @Override
            public void onError(Throwable t) {
                // give throwable back to the client
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                // need to return response and finish communication between server and client
                responseObserver.onNext(DrawResponse.newBuilder().setResult(SB.toString()).build());
                responseObserver.onCompleted();
            }
        } ;
    } // StreamDrawUpdate
}
