package SmartClass.server;

import com.proto.SmartClassroom.*;
import io.grpc.stub.StreamObserver;

public class TestingServiceImpl extends TestingServiceGrpc.TestingServiceImplBase {

    /*
   Unary streaming service for service two method 1
     */
    @Override
    public void CreateTest(TestRequest request, StreamObserver<TestResponse> responseObserver){
        responseObserver.onNext(TestResponse.newBuilder().setResult("testing started " + request.getTheTest()).build());
        responseObserver.onCompleted();
    } // CreateTest
    /*
   Unary streaming service for service two method 2
     */
    @Override
    public void UpLoadTest(UpLoadTestRequest request, StreamObserver<UploadTestResponse> responseObserver){
        responseObserver.onNext(UploadTestResponse.newBuilder().setResult("UploadTest" + request.getRequest()).build());
        responseObserver.onCompleted();
    } // UpLoadTest


    @Override
    public void UpLoadMarks(UpLoadMarksRequest request, StreamObserver<UpLoadMarksResponse> responseObserver){
        responseObserver.onNext(UpLoadMarksResponse.newBuilder().setMarksResponse(request.getMarksRequest()).build());
    }


} // TestingServiceImplTestingServiceImpl
