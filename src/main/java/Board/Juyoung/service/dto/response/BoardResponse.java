package Board.Juyoung.service.dto.response;

import Board.Juyoung.entity.Board;
import Board.Juyoung.entity.Member;
import java.util.List;

public record BoardResponse(Long memberId, String nickname, Long boardId, String title, String content, String image,
                            List<CommentResponse> comments) {

    public static BoardResponse of(Board board, List<CommentResponse> comments) {
        Member member = board.getMember();
        return new BoardResponse(member.getId(), member.getNickname(), board.getId(), board.getTitle(),
            board.getContent(), board.getImage(), comments
        );
    }
}
