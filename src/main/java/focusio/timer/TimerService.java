package focusio.timer;

import focusio.rpc.GrpcServer;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TimerService {

    private static final int TIMER_CYCLE_MILLIS = 100;

    public void startTimer(Long duration) {
        GrpcServer.start();

        var executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(
                new TimerRunner(executorService::shutdownNow, duration),
                0,
                TIMER_CYCLE_MILLIS,
                TimeUnit.MILLISECONDS
        );
    }

    public void stopTimer(String timerId) {

    }
}
