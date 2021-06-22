package focusio.timer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class TimerInfo {
    TimerStatus status;
    long durationMillis;
    long elapsedMillis;
    long startTimestamp;
}
