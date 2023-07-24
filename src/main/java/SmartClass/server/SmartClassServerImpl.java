package SmartClass.server;

import com.proto.SmartClassroom.ContentIdRequest;
import com.proto.SmartClassroom.ContentIdResponse;
import com.proto.SmartClassroom.ContentSelectionGrpc;
import io.grpc.stub.StreamObserver;


public class SmartClassServerImpl extends ContentSelectionGrpc.ContentSelectionImplBase {
    // create a base class for the implementation of our server

    @Override
    public void ContentId(ContentIdRequest request, StreamObserver<ContentIdResponse> responseObserver) {
        responseObserver.onNext(ContentIdResponse.newBuilder().setResult(request.getContentId()).build()); // return a response
        // setting up a ContentIdResponse to be returned to the client
        // now server needs to show the communication is finished
        responseObserver.onCompleted();
    }
}