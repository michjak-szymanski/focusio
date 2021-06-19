package focusio.commands;

import focusio.TimerService;
import picocli.CommandLine.Command;

@Command(name = "list")
public class ListCommand implements Runnable {

    private final TimerService timerService = new TimerService();

    @Override
    public void run() {
        System.out.println("timers list");
    }
}
