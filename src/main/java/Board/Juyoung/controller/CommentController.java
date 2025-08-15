package Board.Juyoung.controller;

import Board.Juyoung.service.CommentService;
import Board.Juyoung.service.dto.request.CommentWriteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글")
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 등록")
    @PostMapping("/{memberId}/{boardId}")
    public ResponseEntity<Void> write(@PathVariable("memberId") Long memberId, @PathVariable("boardId") Long boardId,
        CommentWriteRequest commentWriteRequest) {
        commentService.write(memberId, boardId, commentWriteRequest);
        return ResponseEntity
            .ok()
            .build();
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{memberId}/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable("memberId") Long memberId,
        @PathVariable("commentId") Long commentId) {
        commentService.delete(commentId, memberId);
        return ResponseEntity
            .noContent()
            .build();
    }


}
