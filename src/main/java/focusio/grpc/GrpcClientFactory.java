package focusio.grpc;

import focusio.api.FocusioServiceGrpc;
import io.grpc.ManagedChannelBuilder;

public class GrpcClientFactory {

    public static FocusioServiceGrpc.FocusioServiceBlockingStub createClient() {
        var channel = ManagedChannelBuilder
                .forAddress("localhost", GrpcServer.PORT)
                .usePlaintext()
                .build();
        return FocusioServiceGrpc.newBlockingStub(channel);
    }
}
