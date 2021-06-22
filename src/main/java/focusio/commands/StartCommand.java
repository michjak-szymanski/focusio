package focusio.commands;

import focusio.api.Focusio;
import focusio.grpc.GrpcClientFactory;
import focusio.grpc.GrpcServer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "start")
public class StartCommand implements Runnable {

    @Parameters(index = "0")
    String duration;

    @CommandLine.Option(names = {"--detach", "-d"}, defaultValue = "false")
    Boolean detach;

    @Override
    public void run() {
        var timerId = GrpcClientFactory.createClient().startTimer(
                Focusio.TimerStartRequest.newBuilder()
                        .setDurationMillis(Long.parseLong(duration))
                        .setDetach(detach)
                        .build()
        ).getTimerId();

        System.out.println("Started timer " + timerId);
    }
}
