package Board.Juyoung.service;

import Board.Juyoung.entity.Member;
import Board.Juyoung.exception.custom.MemberNotFoundException;
import Board.Juyoung.repository.MemberRepository;
import Board.Juyoung.service.dto.request.MemberCreateRequest;
import Board.Juyoung.service.dto.request.MemberUpdateRequest;
import Board.Juyoung.service.dto.response.CurrentMemberResponse;
import Board.Juyoung.service.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void register(MemberCreateRequest memberCreateRequest) {
        Member member = new Member(memberCreateRequest.loginId(), memberCreateRequest.email(),
            memberCreateRequest.password(),
            memberCreateRequest.nickname(), memberCreateRequest.profileImage());
        memberRepository.save(member);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public void update(MemberUpdateRequest memberUpdateRequest, Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 유저입니다."));
        member.changeEmail(memberUpdateRequest.email());
        member.changePassword(memberUpdateRequest.password());
        member.changeNickname(memberUpdateRequest.nickname());
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 유저입니다."));
        return MemberResponse.of(member);
    }

    @Transactional(readOnly = true)
    public Page<MemberResponse> getMembers(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        return page.map(MemberResponse::of);
    }

    @Transactional(readOnly = true)
    public CurrentMemberResponse getCurrentMember(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
            .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 유저입니다."));
        return CurrentMemberResponse.of(member);
    }
}
