package Board.Juyoung.exception.handler;

import Board.Juyoung.exception.custom.BoardNotFoundException;
import Board.Juyoung.exception.custom.BoardPermissionDeniedException;
import Board.Juyoung.exception.custom.CommentNotFoundException;
import Board.Juyoung.exception.custom.CommentPermissionDeniedException;
import Board.Juyoung.exception.custom.MemberNotFoundException;
import Board.Juyoung.exception.dto.ErrorResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 글로벌 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse response = ErrorResponse.builder()
            .message("서버에서 오류가 발생했습니다.")
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .timestamp(LocalDateTime.now())
            .details(Collections.singletonList(ex.getClass().getName()))
            .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * 게시판을 찾을 수 없을 때 발생하는 예외 처리
     */
    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBoardNotFound(BoardNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
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
            .message(ex.getMessage())
            .status(HttpStatus.FORBIDDEN.value())
            .timestamp(LocalDateTime.now())
            .details(Collections.singletonList("해당 게시글에 대한 권한을 확인해주세요."))
            .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * 회원을 찾을 수 없을 때 발생하는 예외 처리
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFound(MemberNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .message(ex.getMessage())
            .status(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
            .details(Collections.singletonList("회원 ID를 확인해주세요."))
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 댓글을 찾을 수 없을 때 발생하는 예외 처리
     */
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFound(CommentNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .message(ex.getMessage())
            .status(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
            .details(Collections.singletonList("댓글 ID를 확인해주세요."))
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 댓글 수정 / 삭제 권한이 없을 때 발생하는 예외 처리
     */
    @ExceptionHandler(CommentPermissionDeniedException.class)
    public ResponseEntity<ErrorResponse> handleCommentPermissionDenied(CommentPermissionDeniedException ex) {
        ErrorResponse response = ErrorResponse.builder()
            .message(ex.getMessage())
            .status(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
            .details(Collections.singletonList("해당 댓글에 대한 권한을 확인해주세요."))
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Bean Validation 발생하는 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse response = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .timestamp(LocalDateTime.now())
            .errors(errors)
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

