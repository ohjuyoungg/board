package Board.Juyoung.service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest(String loginId, @NotBlank String email, @NotBlank String password, String nickname,
                                  String profileImage) {

}
