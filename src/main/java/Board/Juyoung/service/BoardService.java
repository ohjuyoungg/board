package Board.Juyoung.service;

import Board.Juyoung.controller.dto.request.BoardUpdateRequest;
import Board.Juyoung.controller.dto.request.BoardWriteRequest;
import Board.Juyoung.controller.dto.response.BoardResponse;
import Board.Juyoung.entity.Board;
import Board.Juyoung.entity.Member;
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
            .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        Board board = new Board(boardWriteRequest.title(), boardWriteRequest.content(), boardWriteRequest.image(),
            member);
        boardRepository.save(board);
    }

    @Transactional
    public void delete(Long memberId) {
        boardRepository.deleteById(memberId);
    }

    @Transactional
    public void update(BoardUpdateRequest boardUpdateRequest, Long memberId) {
        Board board = boardRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 게시판입니다."));
        board.changeContent(boardUpdateRequest.content());
        board.changeTitle(boardUpdateRequest.title());
        board.changeImage(boardUpdateRequest.image());
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long memberId) {
        Board board = boardRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 게시판입니다."));
        return BoardResponse.of(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> getBoards() {
        return boardRepository.findAll().stream()
            .map(BoardResponse::of)
            .toList();
    }
}
