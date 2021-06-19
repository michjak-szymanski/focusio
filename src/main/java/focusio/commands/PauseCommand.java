package focusio.commands;

import focusio.TimerService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "pause")
public class PauseCommand implements Runnable {

    private final TimerService timerService = new TimerService();

    @Parameters(index = "0")
    String timerId;

    @Override
    public void run() {
        System.out.println("Paused timer..." + timerId);
    }
}
