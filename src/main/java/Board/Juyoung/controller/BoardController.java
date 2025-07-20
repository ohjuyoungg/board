package Board.Juyoung.controller;

import Board.Juyoung.controller.dto.request.BoardWriteRequest;
import Board.Juyoung.controller.dto.response.BoardResponse;
import Board.Juyoung.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        @RequestBody BoardWriteRequest boardWriteRequest) {
        boardService.write(memberId, boardWriteRequest);
        return ResponseEntity
            .ok()
            .build();
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getBoards() {
        return ResponseEntity.ok(boardService.getBoards());
    }
}
