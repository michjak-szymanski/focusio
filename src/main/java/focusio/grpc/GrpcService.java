package focusio.grpc;

import com.google.protobuf.Empty;
import focusio.api.Focusio;
import focusio.api.FocusioServiceGrpc;
import focusio.timer.TimerService;
import io.grpc.stub.StreamObserver;
import io.vavr.control.Try;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GrpcService extends FocusioServiceGrpc.FocusioServiceImplBase {

    //todo: refactor to lombok
    private static final Logger LOGGER = Logger.getLogger(GrpcService.class.getName());

    private final TimerService timerService = new TimerService();
    private final MappingService mappingService = MappingService.INSTANCE;

    @Override
    public void startTimer(Focusio.TimerStartRequest request, StreamObserver<Focusio.TimerStartResponse> responseObserver) {
        Try.of(() -> timerService.startTimer(request.getDurationMillis(), request.getDetach()))
                .map(timerId -> Focusio.TimerStartResponse.newBuilder().setTimerId(timerId))
                .onFailure(th -> LOGGER.log(Level.SEVERE, th, () -> "Failed to start timer"))
                .map(Focusio.TimerStartResponse.Builder::build)
                .andThen(responseObserver::onNext)
                .andThen(responseObserver::onCompleted);
    }

    @Override
    public void stopTimer(Focusio.TimerStopRequest request, StreamObserver<Empty> responseObserver) {
        var timerId = request.getTimerId();
        Try.run(() -> timerService.stopTimer(timerId))
                .onFailure(th -> LOGGER.log(Level.SEVERE, th, () -> "Failed to stop timer " + timerId))
                .map(success -> Empty.newBuilder().build())
                .andThen(responseObserver::onNext)
                .andThen(responseObserver::onCompleted);
    }

    @Override
    public void listTimers(Focusio.TimerListRequest request, StreamObserver<Focusio.TimerListResponse> responseObserver) {
        Try.of(timerService::listTimers)
                .onFailure(th -> LOGGER.log(Level.SEVERE, th, () -> "Failed to list timers"))
                .map(Collection::stream)
                .map(tis -> tis.map(mappingService::map))
                .map(tis -> tis.collect(Collectors.toList()))
                .map(tis -> Focusio.TimerListResponse.newBuilder().addAllTimerInfo(tis))
                .map(Focusio.TimerListResponse.Builder::build)
                .andThen(responseObserver::onNext)
                .andThen(responseObserver::onCompleted);
    }
}
