package focusio.timer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class TimerRunner implements Timer {

    private static final String OS_NAME = System.getProperty("os.name");

    private final Runnable onFinishCallback;
    private final long duration;
    private final AtomicReference<TimerStatus> status = new AtomicReference<>();
    private long startTime = 0;
    private long elapsedTime = 0;

    @Override
    public void run() {
        elapsedTime = calculateRemainingTime();

        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        } else if (elapsedTime >= duration) {
            clearConsole();
            System.out.println("Timer finished!");
            status.set(TimerStatus.FINISHED);
            onFinishCallback.run();
        } else if (status.get() == TimerStatus.STOPPED) {
            clearConsole();
            System.out.println("Timer stopped!");
        } else {
            status.set(TimerStatus.STARTED);
//            clearConsole();
            var elapsedTimeStr = String.format("%.1f", (float) elapsedTime / 1000);
//            System.out.println(elapsedTimeStr + "s / " + duration / 1000 + "s");
        }
    }

    @Override
    public void stop() {
        status.set(TimerStatus.STOPPED);
    }

    @Override
    public TimerInfo getState() {
        return new TimerInfo(
                status.get(),
                duration,
                elapsedTime,
                startTime
        );
    }

    private long calculateRemainingTime() {
        return (System.currentTimeMillis() - startTime);
    }

    //todo: extract to utils
    @SneakyThrows
    private void clearConsole() {
        if (OS_NAME.contains("Windows")) {
            Runtime.getRuntime().exec("cls");
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

}
