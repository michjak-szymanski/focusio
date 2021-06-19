package focusio.commands;

import focusio.TimerService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "start")
public class StartCommand implements Runnable {

    private final TimerService timerService = new TimerService();

    @Parameters(index = "0")
    String duration;

    @Override
    public void run() {
        timerService.startTimer(Long.parseLong(duration));
    }
}
