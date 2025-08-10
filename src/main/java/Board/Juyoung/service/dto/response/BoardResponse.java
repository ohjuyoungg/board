package Board.Juyoung.service.dto.response;

import Board.Juyoung.entity.Board;
import Board.Juyoung.entity.Member;

public record BoardResponse(Long memberId, String nickname, Long boardId, String title, String content, String image) {

    public static BoardResponse of(Board board) {
        Member member = board.getMember();
        return new BoardResponse(member.getId(), member.getNickname(), board.getId(), board.getTitle(),
            board.getContent(), board.getImage());
    }
}
