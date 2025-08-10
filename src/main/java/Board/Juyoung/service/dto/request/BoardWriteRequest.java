package Board.Juyoung.service.dto.request;

import jakarta.validation.constraints.NotNull;

public record BoardWriteRequest(@NotNull String title, String content, String image) {

}
