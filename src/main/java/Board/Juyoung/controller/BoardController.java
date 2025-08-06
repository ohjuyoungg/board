package Board.Juyoung.controller;

import Board.Juyoung.controller.dto.request.BoardUpdateRequest;
import Board.Juyoung.controller.dto.request.BoardWriteRequest;
import Board.Juyoung.controller.dto.response.BoardResponse;
import Board.Juyoung.service.BoardService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/{memberId}")
    public ResponseEntity<Void> write(@PathVariable("memberId") Long memberId,
        @RequestBody @Valid BoardWriteRequest boardWriteRequest) {
        boardService.write(memberId, boardWriteRequest);
        return ResponseEntity
            .ok()
            .build();
    }

    @DeleteMapping("{memberId}/{boardId}")
    public ResponseEntity<Void> delete(@PathVariable("memberId") Long memberId, @PathVariable("boardId") Long boardId) {
        boardService.delete(memberId, boardId);
        return ResponseEntity
            .noContent()
            .build();
    }

    @PutMapping("/{memberId}/{boardId}")
    public ResponseEntity<Void> update(@PathVariable("memberId") Long memberId, @PathVariable("boardId") Long boardId,
        @RequestBody BoardUpdateRequest boardUpdateRequest) {
        boardService.update(memberId, boardId, boardUpdateRequest);
        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getBoards() {
        return ResponseEntity.ok(boardService.getBoards());
    }
}
