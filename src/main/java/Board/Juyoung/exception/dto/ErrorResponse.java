package Board.Juyoung.exception.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private final String message;
    private final int status;
    private final LocalDateTime timestamp;
    private List<String> details;
    private Map<String, String> errors;
}
