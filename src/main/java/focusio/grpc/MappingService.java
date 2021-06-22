package focusio.grpc;

import com.google.protobuf.Timestamp;
import focusio.api.Focusio;
import focusio.timer.TimerService;
import focusio.timer.TimerStatus;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MappingService {

    MappingService INSTANCE = Mappers.getMapper(MappingService.class);

    Focusio.TimerInfo map(TimerService.TimerInfoExtended from);

    default Timestamp map(long startTimestamp) {
        return Timestamp.newBuilder().setSeconds(startTimestamp / 1000).build();
    }

    default Focusio.TimerStatus map(TimerStatus from) {
        return Focusio.TimerStatus.valueOf(from.name());
    }
}
