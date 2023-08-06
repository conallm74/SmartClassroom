package SmartClass.server.serviceOne;

import com.proto.SmartClassroom.*;
import io.grpc.stub.StreamObserver;

public class JoinCollabImpl extends CollaborationSessionGrpc.CollaborationSessionImplBase {


    @Override
    public void JoinCollaborationSession(JoinRequest request, StreamObserver<JoinResponse> responseObserver){
        // after receiving one request, we want to get more similar responses
        // define one greet response that will be sent multiple times
        JoinResponse response = JoinResponse.newBuilder().setResult(request.getCollaborationRequest()).build();

        // for loop to send the response 10 times
        for (int i = 0; i < 10; ++i){
            // send response
            responseObserver.onNext(response);
        }
        // close communication between client and server
        responseObserver.onCompleted();
    } // joing collaborationSession

    /*
    Unary streaming for method one service 3
    */

    @Override
    public void CreateColabSession(ColabRequest request, StreamObserver<ColabResponse> responseObserver){
        responseObserver.onNext(ColabResponse.newBuilder().setResult("Creating colab session" + request.getStartCollaborationRequest()).build());
        responseObserver.onCompleted();
    }

}
