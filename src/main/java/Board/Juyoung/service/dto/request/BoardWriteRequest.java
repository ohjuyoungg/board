package Board.Juyoung.service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BoardWriteRequest(@NotBlank String title, @NotBlank String content, String image) {

}
