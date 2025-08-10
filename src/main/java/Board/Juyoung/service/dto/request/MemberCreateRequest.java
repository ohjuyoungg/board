package Board.Juyoung.service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest(@NotBlank String email, @NotBlank String password, String nickname) {

}
