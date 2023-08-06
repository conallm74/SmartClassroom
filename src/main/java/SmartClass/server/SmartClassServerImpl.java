package SmartClass.server;

import com.proto.SmartClassroom.*;
import io.grpc.stub.StreamObserver;

public class SmartClassServerImpl extends ContentSelectionGrpc.ContentSelectionImplBase {

        @Override
        public void ContentNow(ContentIdRequest request, StreamObserver<ContentIdResponse> responseObserver) {
            responseObserver.onNext(ContentIdResponse.newBuilder().setResult(request.getContentId()).build());
            responseObserver.onCompleted();

        } // ContentNow
    /*
    // client side streaming for service one method 3
     */

        @Override
        public StreamObserver<DisplayCommandRequest> StreamDisplayUpdate (StreamObserver<DisplayUpdateResponse> responseObserver){
            StringBuilder stringer = new StringBuilder();
            return new StreamObserver<DisplayCommandRequest>() {
                @Override
                public void onNext(DisplayCommandRequest request) {
                    stringer.append("Obtaining String");
                    stringer.append(request.getDisplay());
                    stringer.append("!\n");
                }

                @Override
                public void onError(Throwable t) {
                    responseObserver.onError(t);
                }

                @Override
                public void onCompleted() {
                    // need to return response and finish communication between server and client
                    responseObserver.onNext(DisplayUpdateResponse.newBuilder().setDisplaying(stringer.toString()).build());
                    responseObserver.onCompleted();

                }
            };
        } // StreamDisplayUpdate
    } // SmartClassImp
