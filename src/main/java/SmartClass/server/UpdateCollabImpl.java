package SmartClass.server;

import com.proto.SmartClassroom.TheCollaborationUpdatesGrpc;
import com.proto.SmartClassroom.UpdateCollaborationRequest;
import com.proto.SmartClassroom.UpdateCollaborationResponse;
import io.grpc.stub.StreamObserver;

public class UpdateCollabImpl extends TheCollaborationUpdatesGrpc.TheCollaborationUpdatesImplBase {

    @Override
    // public StreamObserver<Request> rpc(StreamObserver<Response>)
    public StreamObserver<UpdateCollaborationRequest> UpdateCollaborationSession(StreamObserver<UpdateCollaborationResponse> responseObserver){
        return new StreamObserver<UpdateCollaborationRequest>() {
            @Override
            public void onNext(UpdateCollaborationRequest request) {
                // upon request, directly return response
                responseObserver.onNext(UpdateCollaborationResponse.newBuilder().setResult(request.getUpdateRequest()).build());
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }


}
