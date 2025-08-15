package Board.Juyoung.service.dto.response;

import Board.Juyoung.entity.Member;

public record MemberCurrentResponse(Long id, String nickname) {

    public static MemberCurrentResponse of(Member member) {
        return new MemberCurrentResponse(member.getId(), member.getNickname());
    }
}
