package Board.Juyoung.service;

import Board.Juyoung.controller.dto.request.BoardUpdateRequest;
import Board.Juyoung.controller.dto.request.BoardWriteRequest;
import Board.Juyoung.controller.dto.response.BoardResponse;
import Board.Juyoung.entity.Board;
import Board.Juyoung.entity.Member;
import Board.Juyoung.exception.custom.MemberNotFoundException;
import Board.Juyoung.repository.BoardRepository;
import Board.Juyoung.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void write(Long memberId, BoardWriteRequest boardWriteRequest) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 유저입니다."));
        Board board = new Board(boardWriteRequest.title(), boardWriteRequest.content(), boardWriteRequest.image(),
            member);
        boardRepository.save(board);
    }

    @Transactional
    public void delete(Long memberId, Long boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다."));
        Long boardMemberId = board.getMember().getId();
        if (!memberId.equals(boardMemberId)) {
            throw new IllegalArgumentException("해당 게시글을 삭제할 수 있는 권한이 없습니다.");
        }
        boardRepository.deleteById(boardId);
    }

    @Transactional
    public void update(Long memberId, Long boardId, BoardUpdateRequest boardUpdateRequest) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다."));
        Long boardMemberId = board.getMember().getId();
        if (!memberId.equals(boardMemberId)) {
            throw new IllegalArgumentException("해당 게시글을 수정할 수 있는 권한이 없습니다.");
        }
        board.changeContent(boardUpdateRequest.content());
        board.changeTitle(boardUpdateRequest.title());
        board.changeImage(boardUpdateRequest.image());
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다."));
        return BoardResponse.of(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> getBoards() {
        return boardRepository.findAll().stream()
            .map(BoardResponse::of)
            .toList();
    }
}
