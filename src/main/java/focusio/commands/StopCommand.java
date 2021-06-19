package focusio.commands;

import focusio.TimerService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "stop")
public class StopCommand implements Runnable {

    private final TimerService timerService = new TimerService();

    @Parameters(index = "0")
    String timerId;

    @Override
    public void run() {
        System.out.println("Stopped timer..." + timerId);
    }
}
