package Board.Juyoung.service;

import Board.Juyoung.controller.dto.request.MemberCreateRequest;
import Board.Juyoung.entity.Member;
import Board.Juyoung.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
