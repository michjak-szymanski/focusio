package focusio.commands;

import focusio.api.Focusio;
import focusio.grpc.GrpcClientFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "stop")
public class StopCommand implements Runnable {

    @Parameters(index = "0")
    String timerId;

    @Override
    public void run() {
        //noinspection ResultOfMethodCallIgnored
        GrpcClientFactory.createClient().stopTimer(
                Focusio.TimerStopRequest
                        .newBuilder()
                        .setTimerId(timerId)
                        .build()
        );
        System.out.println("Stopped timer " + timerId);
    }
}
