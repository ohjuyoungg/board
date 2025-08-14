package Board.Juyoung.controller;

import Board.Juyoung.service.MemberService;
import Board.Juyoung.service.dto.request.MemberCreateRequest;
import Board.Juyoung.service.dto.request.MemberUpdateRequest;
import Board.Juyoung.service.dto.response.MemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원")
@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 등록")
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody MemberCreateRequest memberCreateRequest) {
        memberService.register(memberCreateRequest);
        return ResponseEntity
            .ok()
            .build();
    }

    @Operation(summary = "회원 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        memberService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @Operation(summary = "회원 수정")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,
        @RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberService.update(memberUpdateRequest, id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @Operation(summary = "회원 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }

    @Operation(summary = "회원 목록 조회")
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }
}
