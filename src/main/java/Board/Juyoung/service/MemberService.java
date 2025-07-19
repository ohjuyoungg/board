package Board.Juyoung.service;

import Board.Juyoung.controller.dto.request.MemberCreateRequest;
import Board.Juyoung.controller.dto.request.MemberUpdateRequest;
import Board.Juyoung.entity.Member;
import Board.Juyoung.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public void register(MemberCreateRequest memberCreateRequest) {
        Member member = new Member(memberCreateRequest.email(), memberCreateRequest.password(),
            memberCreateRequest.nickname());
        memberRepository.save(member);
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public void update(MemberUpdateRequest memberUpdateRequest, Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        member.changeEmail(memberUpdateRequest.email());
        member.changePassword(memberUpdateRequest.password());
        member.changeNickname(memberUpdateRequest.nickname());
    }
}
