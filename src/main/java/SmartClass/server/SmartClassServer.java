package SmartClass.server;

import SmartClass.server.serviceOne.JoinCollabImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class SmartClassServer {
    // create a base class for the implements
    public static void main(String[] args) throws IOException, InterruptedException {
        // define a port for the server to listen for client requests
        int port = 50051;

        // create and instance of the grpc server
        Server server = ServerBuilder
                .forPort(port)
                // register service for the grpc server
                // next need to write client code that will call this function on the server and print answer in terminal
                .addService(new SmartClassServerImpl())
                .addService(new JoinCollabImpl())
                .addService(new UpdateCollabImpl())
                .addService(new TestingServiceImpl())
                .addService(new JoinCollabImpl())
                .addService(new StreamDrawImpl())
                .build();

        // start the server
        server.start();
        // create visual feedback of what's happening in the code
        System.out.println("server has started");
        System.out.println("listening on port: " + port);

        // handling shut down of the server
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // visual confirmation the thread is called
            System.out.println("received shut down request");
            server.shutdown();
            System.out.println("Server stopped");

        }));
        // wait for shut down request
        server.awaitTermination();
    }
}
