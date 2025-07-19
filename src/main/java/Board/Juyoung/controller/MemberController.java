package Board.Juyoung.controller;

import Board.Juyoung.controller.dto.request.MemberCreateRequest;
import Board.Juyoung.controller.dto.request.MemberUpdateRequest;
import Board.Juyoung.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public String register(@RequestBody MemberCreateRequest memberCreateRequest) {
        memberService.register(memberCreateRequest);
        return "ok";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        memberService.delete(id);
        return "ok";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberService.update(memberUpdateRequest, id);
        return "ok";
    }
}
