package Board.Juyoung.service;

import Board.Juyoung.entity.Board;
import Board.Juyoung.entity.Comment;
import Board.Juyoung.entity.Member;
import Board.Juyoung.exception.custom.BoardNotFoundException;
import Board.Juyoung.exception.custom.MemberNotFoundException;
import Board.Juyoung.repository.BoardRepository;
import Board.Juyoung.repository.CommentRepository;
import Board.Juyoung.repository.MemberRepository;
import Board.Juyoung.service.dto.request.CommentUpdateRequest;
import Board.Juyoung.service.dto.request.CommentWriteRequest;
import Board.Juyoung.service.dto.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void write(Long memberId, Long boardId, CommentWriteRequest commentWriteRequest) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 유저입니다."));
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시판입니다."));
        Comment comment = new Comment(commentWriteRequest.content(), board, member);
        commentRepository.save(comment);
    }

    @Transactional
    public void delete(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 댓글입니다."));
        Long commentMemberId = comment.getMember().getId();
        if (!commentMemberId.equals(memberId)) {
            throw new RuntimeException("해당 댓글을 삭제할 수 있는 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }

    @Transactional
    public void update(Long commentId, Long memberId, CommentUpdateRequest commentUpdateRequest) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 댓글입니다."));
        Long commentMemberId = comment.getMember().getId();
        if (!commentMemberId.equals(memberId)) {
            throw new RuntimeException("해당 댓글을 삭제할 수 있는 권한이 없습니다.");
        }
        comment.changeContent(commentUpdateRequest.content());
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getComments(Long boardId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByBoardIdOrderByCreatedDateAsc(boardId, pageable);
        return comments.map(CommentResponse::of);
    }
}
