package Board.Juyoung.exception.handler;

import Board.Juyoung.exception.custom.MemberNotFoundException;
import Board.Juyoung.exception.dto.ErrorResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MemberExceptionHandler {

    /**
     * 회원을 찾을 수 없을 때 발생하는 예외 처리
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFound(MemberNotFoundException ex) {
        log.error("test");
        ErrorResponse response = ErrorResponse.builder()
            .code("MEMBER_NOT_FOUND")
            .message(ex.getMessage()) // 예외에서 전달된 메시지 사용
            .status(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
            .details(Collections.singletonList("회원 ID를 확인해주세요."))
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

