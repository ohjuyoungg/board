package Board.Juyoung.exception.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private final String code;       // 에러 코드
    private final String message;    // 에러 메시지
    private final int status;        // HTTP 상태 코드
    private final LocalDateTime timestamp; // 발생 시각
    private final List<String> details;    // 추가 정보
}
