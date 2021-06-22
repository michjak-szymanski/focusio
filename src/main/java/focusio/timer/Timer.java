package focusio.timer;

public interface Timer extends Runnable {

    void stop();

    TimerInfo getState();

}
