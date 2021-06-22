package focusio.commands;

import focusio.grpc.GrpcServer;
import picocli.CommandLine;

@CommandLine.Command(name = "init")
public class InitCommand implements Runnable {

    @Override
    public void run() {
        GrpcServer.start();
    }
}
