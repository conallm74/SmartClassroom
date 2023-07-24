package SmartClass.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SmartClassClient {
    public static void main(String[] args) {
        // Channels to create the TCP connection between the client and the server
        // create channel
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localHost", 50051)
                .usePlaintext()
                // build channel object
                .build();

        // future code to do things

        // create visual feedback for the channel shutdown
        System.out.println("the channel has shut down");
        // need to close the channel after executing the code
        channel.shutdown();



    }
}
