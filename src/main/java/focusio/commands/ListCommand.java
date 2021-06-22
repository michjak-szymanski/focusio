package focusio.commands;

import focusio.api.Focusio;
import focusio.grpc.GrpcClientFactory;
import picocli.CommandLine.Command;

import java.time.Instant;
import java.util.StringJoiner;

@Command(name = "list")
public class ListCommand implements Runnable {

    @Override
    public void run() {
        var timersInfo = GrpcClientFactory.createClient()
                .listTimers(Focusio.TimerListRequest.getDefaultInstance())
                .getTimerInfoList();

        System.out.println("id | state | duration | elapsed | started");

        timersInfo.forEach(ti -> {
            var joiner = new StringJoiner(" | ");
            joiner.add(ti.getTimerId());
            joiner.add(String.valueOf(ti.getStatus()));
            joiner.add(String.valueOf(ti.getDurationMillis()));
            joiner.add(String.valueOf(ti.getElapsedMillis()));
            joiner.add(Instant.ofEpochSecond(ti.getStartTimestamp().getSeconds()).toString());
            System.out.println(joiner);
        });
    }
}
