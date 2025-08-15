package Board.Juyoung.service.dto.response;

import Board.Juyoung.entity.Member;

public record CurrentMemberResponse(Long id, String nickname) {

    public static CurrentMemberResponse of(Member member) {
        return new CurrentMemberResponse(member.getId(), member.getNickname());
    }
}
