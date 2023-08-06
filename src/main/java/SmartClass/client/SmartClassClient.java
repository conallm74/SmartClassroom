package SmartClass.client;

import com.proto.SmartClassroom.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


// setting up the client to communicate with the server
public class SmartClassClient {

    public static void doContent (ManagedChannel channel){
        // visual feedback for using the function
        System.out.println("Enter the content ID");
        // create a blocking stub to interact with Content Selection in proto
        ContentSelectionGrpc.ContentSelectionBlockingStub stub = ContentSelectionGrpc.newBlockingStub(channel);
        ContentIdResponse response = stub.contentNow(ContentIdRequest.newBuilder().setContentId(Integer.parseInt(String.valueOf(44))).build());

        System.out.println("Retrieved content :" + response.getResult());

        try {
            response = stub.contentNow(ContentIdRequest.newBuilder().setContentId(Integer.parseInt("Hello")).build());
        } catch (StringIndexOutOfBoundsException s){
            System.out.println("Content ID must be numeric");
        }
    }

    /*
   Unary streaming service for service two method 1
     */
    private static void doCreateTest(ManagedChannel channel) {
        // visual feeckback for using function
        System.out.println("DoCreateTest");
        // create a blocking stub to interact with testing in proto
        TestingServiceGrpc.TestingServiceBlockingStub stub = TestingServiceGrpc.newBlockingStub(channel);
        TestResponse response = stub.createTest(TestRequest.newBuilder().setTheTest("Test").build());

        System.out.println("The test: " + response.getResult());

        try {
            response = stub.createTest(TestRequest.newBuilder().setTheTest(String.valueOf(1)).build());
        } catch (Exception e){
            System.out.println("Got an exception");
            e.printStackTrace();
        }
    }
     /*
   Unary streaming service for service two method 2
     */
    private static void doUpLoadTest(ManagedChannel channel) {
        System.out.println("DoUpLoadTest");
        TestingServiceGrpc.TestingServiceBlockingStub stub = TestingServiceGrpc.newBlockingStub(channel);
        UploadTestResponse response = stub.upLoadTest(UpLoadTestRequest.newBuilder().setRequest("Uploading test").build());

        System.out.println("System is: " + response.getResult());

        try {
            response = stub.upLoadTest(UpLoadTestRequest.newBuilder().setRequest(String.valueOf(1)).build());
        } catch (Exception e){
            System.out.println("Caught and exception");
            e.printStackTrace();
        }
    }
    private static void doUpLoadMarks(ManagedChannel channel) {
        System.out.println("UpLoadMarks");
        TestingServiceGrpc.TestingServiceBlockingStub stub = TestingServiceGrpc.newBlockingStub(channel);
        UploadTestResponse marks_response = stub.upLoadMarks(UpLoadMarksRequest.newBuilder().setMarksRequest("Marks uploaded").build());
        System.out.println("System has" + marks_response.getResult());

        try {
            marks_response = stub.upLoadMarks(UpLoadMarksRequest.newBuilder().setMarksRequest(String.valueOf(1)).build());
        } catch (Exception e) {
            System.out.println("Caught and exception");
            e.printStackTrace();
            }

    /*
    Unary streaming for method one service 3
     */
    private static void doCreateColabSession(ManagedChannel channel) {
        System.out.println("DoCreateColabSession");
        CollaborationSessionGrpc.CollaborationSessionBlockingStub stuber = CollaborationSessionGrpc.newBlockingStub(channel);
        ColabResponse response = stuber.createColabSession(ColabRequest.newBuilder().setStartCollaborationRequest("Created session").build());

        System.out.println("System has " + response.getResult());
    }


    /*
   // client side streaming for service one method 2 endpoint client side
    */

        private static void doStreamDrawUpdates(ManagedChannel channel) throws InterruptedException {
            System.out.println("Enter doStreamDrawUpdates");
            // define asynchronous stub as it's streaming
            drawingMateGrpc.drawingMateStub stub = drawingMateGrpc.newStub(channel);
            // list of Strings we'll send to the server
            List<String> names = new ArrayList<>();
            // waiting for asynchronous servers responses
            CountDownLatch Latch = new CountDownLatch(1);
            Collections.addAll(names, "School", "Work", "now");
            // populate list
            Collections.addAll(names, "School", "Work", "now");

            StreamObserver<DrawRequest> stream = stub.streamDrawUpdates(new StreamObserver<DrawResponse>() {
                @Override
                public void onNext(DrawResponse response) {
                    // visual confirmation of the response
                    System.out.println("Response" + response.getResult());
                }

                @Override
                public void onError(Throwable t) {

                }

                @Override
                public void onCompleted() {
                    // make latch equal zero so that we can quit the program
                    Latch.countDown();
                }
            });
            // use stream by iterating through the names \
            for (String name : names){
                stream.onNext(DrawRequest.newBuilder().setDraw(name).build());
            }
            stream.onCompleted();
            Latch.await(10, TimeUnit.SECONDS);
        }


    public static void main(String[] args) throws InterruptedException {
        // check if the args are an RPC endpoint
        // by checking if the args length equals zero
        if (args.length == 0){


            System.out.println("Needs argument to work");
            return;
        }
        // Channels to create the TCP connection between the client and the server
        // create channel
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localHost", 50051)
                .usePlaintext()
                // build channel object
                .build();
        // switch, I wonder why we need a switch here
        // the switch will check the first argument
        switch (args[0]){
            // case: [rpc name]: doRpc(channel) break;
            case "getContent": doContent(channel);break;
            case "Stream_Draw_Updates": doStreamDrawUpdates(channel); break;
            case "Join_Collaboration_Session": doJoinCollaborationSession(channel); break;
            case "Update_Collaboration_Session": doUpdateCollaborationSession(channel); break;
            case "Create_Test": doCreateTest(channel); break;
            case "Up_Load_Test": doUpLoadTest(channel);break;
            case "Create_Colab_Session": doCreateColabSession(channel); break;
            case "Up_Load_Marks": doUpLoadMarks(channel); break;

            // create a default in-case a string is sent that isn't known by the switch, the default is called
            default:
                System.out.println("Keyword Invalid: " + args[0]);
        }
        // create visual feedback for the channel shutdown
        System.out.println("the channel has shut down");
        // need to close the channel after executing the code
        channel.shutdown();
    }



    private static void doUpdateCollaborationSession(ManagedChannel channel) throws InterruptedException {
        System.out.println("Entering doUpdateCollaborationSession");
        TheCollaborationUpdatesGrpc.TheCollaborationUpdatesStub stub = TheCollaborationUpdatesGrpc.newStub(channel);
        // countdown latch as we're in an asynchronous situation
        CountDownLatch latch = new CountDownLatch(1);
        // get stream observer of UpdateCollaborationRequest to send multiple requests to the server

        StreamObserver<UpdateCollaborationRequest> stream = stub.updateCollaborationSession(new StreamObserver<UpdateCollaborationResponse>() {
            @Override
            public void onNext(UpdateCollaborationResponse response) {
                System.out.println(response.getResult());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                // decrease latch count
                latch.countDown();
            }
        });
        // need to send multiple requests, call onNext function on the stream
        Arrays.asList("Test", "Updating", "Test finished").forEach(name ->
                stream.onNext(UpdateCollaborationRequest.newBuilder().setUpdateRequest(name).build()));
        // inform server communication is now finished
        stream.onCompleted();
        latch.await(3, TimeUnit.SECONDS);

    }

     /*
        For service three Method 2, server side streaming
         */

    private static void doJoinCollaborationSession(ManagedChannel channel) {
        System.out.println("Enter doJoinCollaborationSession");
        // double check if it should be a stub or a BlockingStub
        CollaborationSessionGrpc.CollaborationSessionBlockingStub stub = CollaborationSessionGrpc.newBlockingStub(channel);
        stub.joinCollaborationSession(JoinRequest.newBuilder().setCollaborationRequest("Session Joined").build()).forEachRemaining(response -> {
            System.out.println(response.getResult());
        });

    }


}
