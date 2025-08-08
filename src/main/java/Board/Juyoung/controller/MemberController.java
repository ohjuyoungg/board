package Board.Juyoung.controller;

import Board.Juyoung.controller.dto.request.MemberCreateRequest;
import Board.Juyoung.controller.dto.request.MemberUpdateRequest;
import Board.Juyoung.controller.dto.response.MemberResponse;
import Board.Juyoung.exception.dto.ErrorResult;
import Board.Juyoung.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExceptionHandler(IllegalArgumentException e) {
        return new ErrorResult("잘못된 입력값입니다.", e.getMessage());
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody MemberCreateRequest memberCreateRequest) {
        memberService.register(memberCreateRequest);
        return ResponseEntity
            .ok()
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        memberService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,
        @RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberService.update(memberUpdateRequest, id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }
}
