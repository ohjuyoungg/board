package Board.Juyoung.exception.handler;

import Board.Juyoung.exception.custom.BoardNotFoundException;
import Board.Juyoung.exception.custom.BoardPermissionDeniedException;
import Board.Juyoung.exception.dto.ErrorResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardExceptionHandler {

    /**
     * 게시판을 찾을 수 없을 때 발생하는 예외 처리
     */
    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBoardNotFound(BoardNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .code("BOARD_NOT_FOUND")
            .message(ex.getMessage())
            .status(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
            .details(Collections.singletonList("게시판 ID를 확인해주세요."))
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 게시판 수정 / 삭제 권한이 없을 때 발생하는 예외 처리
     */
    @ExceptionHandler(BoardPermissionDeniedException.class)
    public ResponseEntity<ErrorResponse> handleBoardPermissionDenied(BoardPermissionDeniedException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .code("BOARD_PERMISSION_DENIED")
            .message(ex.getMessage())
            .status(HttpStatus.FORBIDDEN.value())
            .timestamp(LocalDateTime.now())
            .details(Collections.singletonList("해당 게시글에 대한 권한을 확인해주세요."))
            .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
