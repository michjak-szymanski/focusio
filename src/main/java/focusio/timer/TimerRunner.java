package focusio.timer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class TimerRunner implements Runnable {

    private static final String OS_NAME = System.getProperty("os.name");

    private final Runnable onFinishCallback;
    private final long duration;
    private long startTime = 0;

    @Override
    public void run() {
        float elapsedTime = calculateRemainingTime();

        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        } else if (elapsedTime >= duration) {
            clearConsole();
            System.out.println("Timer finished!");
            onFinishCallback.run();
        } else {
            clearConsole();
            var elapsedTimeStr = String.format("%.1f", elapsedTime / 1000);
            System.out.println(elapsedTimeStr + "s / " + duration / 1000 + "s");
        }
    }

    private long calculateRemainingTime() {
        return (System.currentTimeMillis() - startTime);
    }

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
