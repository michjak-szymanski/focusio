package focusio.rpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.SneakyThrows;

import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcServer {

    //todo: refactor to lombok
    private static final Logger LOGGER = Logger.getLogger(GrpcServer.class.getName());

    //todo: convert to random port + find a way to retrieve random port by client...
    static final int PORT = 15099;

    private static final Server SERVER = ServerBuilder.forPort(PORT)
            .addService(new GrpcService())
            .executor(Executors.newFixedThreadPool(3))
            .build();

    @SneakyThrows
    public static void start() {
        SERVER.start();
        LOGGER.log(Level.INFO, "Started focusio server at port " + PORT);
    }
}
