package Board.Juyoung.service.dto.response;

import Board.Juyoung.entity.Member;

public record MemberResponse(Long id, String email, String password, String nickname) {

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getPassword(), member.getNickname());
    }
}
