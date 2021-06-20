package focusio.rpc;

import focusio.timer.TimerService;
import io.grpc.stub.StreamObserver;
import io.vavr.control.Try;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcService extends FocusioServiceGrpc.FocusioServiceImplBase {

    //todo: refactor to lombok
    private static final Logger LOGGER = Logger.getLogger(GrpcService.class.getName());

    private final TimerService timerService = new TimerService();

    @Override
    public void stopTimer(Focusio.TimerSimpleRequest request, StreamObserver<Focusio.TimerSimpleResponse> responseObserver) {
        var timerId = request.getTimerId();
        Try.run(() -> timerService.stopTimer(timerId))
                .map(__ -> true)
                .onFailure(th -> LOGGER.log(Level.SEVERE, th, () -> "Failed to stop timer " + timerId))
                .recover(__ -> false)
                .map(success -> Focusio.TimerSimpleResponse.newBuilder().setSuccess(success))
                .map(Focusio.TimerSimpleResponse.Builder::build)
                .andThen(responseObserver::onNext)
                .andThen(responseObserver::onCompleted);
    }
}
