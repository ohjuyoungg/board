package Board.Juyoung.service;

import Board.Juyoung.entity.Board;
import Board.Juyoung.entity.Member;
import Board.Juyoung.exception.custom.BoardNotFoundException;
import Board.Juyoung.exception.custom.BoardPermissionDeniedException;
import Board.Juyoung.exception.custom.MemberNotFoundException;
import Board.Juyoung.repository.BoardRepository;
import Board.Juyoung.repository.MemberRepository;
import Board.Juyoung.service.dto.request.BoardUpdateRequest;
import Board.Juyoung.service.dto.request.BoardWriteRequest;
import Board.Juyoung.service.dto.response.BoardResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Transactional
    public void write(Long memberId, BoardWriteRequest boardWriteRequest, MultipartFile image) throws IOException {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 유저입니다."));
        String imageURL = null;
        if (image != null && !image.isEmpty()) {
            imageURL = s3Service.uploadImage(image);
        }
        Board board = new Board(boardWriteRequest.title(), boardWriteRequest.content(), imageURL, member);
        boardRepository.save(board);
    }

    @Transactional
    public void delete(Long memberId, Long boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시판입니다."));
        Long boardMemberId = board.getMember().getId();
        if (!memberId.equals(boardMemberId)) {
            throw new BoardPermissionDeniedException("해당 게시글을 삭제할 수 있는 권한이 없습니다.");
        }
        boardRepository.deleteById(boardId);
    }

    @Transactional
    public void update(Long memberId, Long boardId, BoardUpdateRequest boardUpdateRequest, MultipartFile image)
        throws IOException {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시판입니다."));
        Long boardMemberId = board.getMember().getId();
        if (!memberId.equals(boardMemberId)) {
            throw new BoardPermissionDeniedException("해당 게시글을 수정할 수 있는 권한이 없습니다.");
        }
        board.changeContent(boardUpdateRequest.content());
        board.changeTitle(boardUpdateRequest.title());
        if (image != null && !image.isEmpty()) {
            String imageURL = s3Service.uploadImage(image);
            board.changeImage(imageURL);
        }
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시판입니다."));
        return BoardResponse.of(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> getBoards() {
        return boardRepository.findAll().stream()
            .map(BoardResponse::of)
            .toList();
    }
}
