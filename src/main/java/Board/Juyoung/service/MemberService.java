package Board.Juyoung.service;

import Board.Juyoung.entity.Member;
import Board.Juyoung.exception.custom.MemberNotFoundException;
import Board.Juyoung.repository.MemberRepository;
import Board.Juyoung.service.dto.request.MemberCreateRequest;
import Board.Juyoung.service.dto.request.MemberUpdateRequest;
import Board.Juyoung.service.dto.response.MemberResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void register(MemberCreateRequest memberCreateRequest) {
        Member member = new Member(memberCreateRequest.email(), memberCreateRequest.password(),
            memberCreateRequest.nickname());
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
    public List<MemberResponse> getMembers() {
        return memberRepository.findAll().stream()
            .map(MemberResponse::of) // m -> MemberResponse.of(m)
            .toList();
    }
}
