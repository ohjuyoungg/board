package Board.Juyoung.service;

import Board.Juyoung.entity.Board;
import Board.Juyoung.entity.Member;
import Board.Juyoung.exception.custom.BoardNotFoundException;
import Board.Juyoung.exception.custom.BoardPermissionDeniedException;
import Board.Juyoung.exception.custom.MemberNotFoundException;
import Board.Juyoung.repository.BoardRepository;
import Board.Juyoung.repository.CommentRepository;
import Board.Juyoung.repository.MemberRepository;
import Board.Juyoung.service.dto.request.BoardUpdateRequest;
import Board.Juyoung.service.dto.request.BoardWriteRequest;
import Board.Juyoung.service.dto.response.BoardListResponse;
import Board.Juyoung.service.dto.response.BoardResponse;
import Board.Juyoung.service.dto.response.CommentResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final S3Service s3Service;

    @Transactional
    public void write(Long memberId, BoardWriteRequest boardWriteRequest, MultipartFile image) {
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
        String imageUrl = board.getImage();
        if (imageUrl != null) {
            s3Service.deleteImage(imageUrl);
        }
        boardRepository.delete(board);
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
        String imageUrl = board.getImage();
        board.changeContent(boardUpdateRequest.content());
        board.changeTitle(boardUpdateRequest.title());
        if (imageUrl != null) {
            s3Service.deleteImage(imageUrl);
            board.changeImage(s3Service.uploadImage(image));
        }
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long boardId, Pageable pageable) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시판입니다."));
        List<CommentResponse> commentDTO = commentRepository.findByBoardIdOrderByCreatedDateAsc(boardId, pageable)
            .stream()
            .map(CommentResponse::of)
            .toList();
        return BoardResponse.of(board, commentDTO);
    }

    @Transactional(readOnly = true)
    public Page<BoardListResponse> getBoards(Pageable pageable) {
        Page<Board> boards = boardRepository.findByOrderByCreatedDateDesc(pageable);
        return boards.map(BoardListResponse::of);
    }
}
