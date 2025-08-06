package Board.Juyoung.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record BoardWriteRequest(@NotNull String title, String content, String image) {

}
