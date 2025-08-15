package Board.Juyoung.service;

import Board.Juyoung.entity.Board;
import Board.Juyoung.entity.Comment;
import Board.Juyoung.entity.Member;
import Board.Juyoung.exception.custom.BoardNotFoundException;
import Board.Juyoung.exception.custom.MemberNotFoundException;
import Board.Juyoung.repository.BoardRepository;
import Board.Juyoung.repository.CommentRepository;
import Board.Juyoung.repository.MemberRepository;
import Board.Juyoung.service.dto.request.CommentWriteRequest;
import lombok.RequiredArgsConstructor;
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
}
