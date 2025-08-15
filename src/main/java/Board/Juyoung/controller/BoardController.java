package Board.Juyoung.controller;

import Board.Juyoung.service.BoardService;
import Board.Juyoung.service.dto.request.BoardUpdateRequest;
import Board.Juyoung.service.dto.request.BoardWriteRequest;
import Board.Juyoung.service.dto.response.BoardListResponse;
import Board.Juyoung.service.dto.response.BoardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "게시물")
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시물 등록")
    @PostMapping("/{memberId}")
    public ResponseEntity<Void> write(@PathVariable("memberId") Long memberId,
        BoardWriteRequest boardWriteRequest,
        @RequestPart(required = false) MultipartFile image) {
        boardService.write(memberId, boardWriteRequest, image);
        return ResponseEntity
            .ok()
            .build();
    }

    @Operation(summary = "게시물 삭제")
    @DeleteMapping("{memberId}/{boardId}")
    public ResponseEntity<Void> delete(@PathVariable("memberId") Long memberId, @PathVariable("boardId") Long boardId) {
        boardService.delete(memberId, boardId);
        return ResponseEntity
            .noContent()
            .build();
    }

    @Operation(summary = "게시물 수정")
    @PutMapping("/{memberId}/{boardId}")
    public ResponseEntity<Void> update(@PathVariable("memberId") Long memberId, @PathVariable("boardId") Long boardId,
        BoardUpdateRequest boardUpdateRequest,
        @RequestPart(required = false) MultipartFile image)
        throws IOException {
        boardService.update(memberId, boardId, boardUpdateRequest, image);
        return ResponseEntity
            .noContent()
            .build();
    }

    @Operation(summary = "게시물 단건 조회")
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable("boardId") Long boardId, Pageable pageable) {
        return ResponseEntity.ok(boardService.getBoard(boardId, pageable));
    }

    @Operation(summary = "게시물 목록 조회")
    @GetMapping
    public ResponseEntity<Page<BoardListResponse>> getBoards(
        @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(boardService.getBoards(pageable));
    }
}
