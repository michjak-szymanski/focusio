package focusio.commands;

import focusio.rpc.Focusio;
import focusio.rpc.GrpcClientFactory;
import focusio.timer.TimerService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "stop")
public class StopCommand implements Runnable {

    private final TimerService timerService = new TimerService();

    @Parameters(index = "0")
    String timerId;

    @Override
    public void run() {
        var result = GrpcClientFactory.createClient().stopTimer(
                Focusio.TimerSimpleRequest
                        .newBuilder()
                        .setTimerId(timerId)
                        .build()
        );
        if (result.getSuccess()) {
            System.out.println("Stopped timer " + timerId);
        } else{
            System.out.println("Failed to stop timer " + timerId + " !");
        }
    }

}
