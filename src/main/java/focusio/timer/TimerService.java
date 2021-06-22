package focusio.timer;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

//TODO: export as library
public class TimerService {

    private static final int TIMER_CYCLE_MILLIS = 100;

    private ConcurrentHashMap<String, TimerRunner> timers = new ConcurrentHashMap<>();

    public String startTimer(Long durationMillis, Boolean detach) {
        var executorService = Executors.newSingleThreadScheduledExecutor();
        var timer = new TimerRunner(executorService::shutdownNow, durationMillis);
        executorService.scheduleAtFixedRate(timer, 0, TIMER_CYCLE_MILLIS, MILLISECONDS);
        return registerTimer(timer);
    }

    public void stopTimer(String timerId) {
        var timer = timers.get(timerId);
        timer.stop();
    }

    public List<TimerInfoExtended> listTimers() {
        return timers.entrySet().stream()
                .map(e -> Map.entry(e.getKey(), e.getValue().getState()))
                .map(e -> new TimerInfoExtended(
                        e.getKey().toString(),
                        e.getValue().status,
                        e.getValue().durationMillis,
                        e.getValue().elapsedMillis,
                        e.getValue().startTimestamp
                ))
                .collect(Collectors.toList());
    }

    private String registerTimer(TimerRunner timerRunner) {
        var id = UUID.randomUUID().toString().replace("-", "");
        timers.put(id, timerRunner);
        return id;
    }

    @Getter
    public static class TimerInfoExtended extends TimerInfo {

        private final String timerId;

        public TimerInfoExtended(
                String id,
                TimerStatus state,
                long duration,
                long elapsedTime,
                long startTime
        ) {
            super(state, duration, elapsedTime, startTime);
            this.timerId = id;
        }
    }
}
