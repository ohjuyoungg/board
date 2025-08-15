package Board.Juyoung.service.dto.response;

import Board.Juyoung.entity.Board;
import Board.Juyoung.entity.Member;

public record BoardListResponse(Long memberId, String nickname, Long boardId, String title, String content,
                                String image) {

    public static BoardListResponse of(Board board) {
        Member member = board.getMember();
        return new BoardListResponse(member.getId(), member.getNickname(), board.getId(), board.getTitle(),
            board.getContent(), board.getImage()
        );
    }
}
