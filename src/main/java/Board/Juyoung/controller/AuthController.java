package Board.Juyoung.controller;

import Board.Juyoung.jwt.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "access Token 발급")
@RequiredArgsConstructor
@RequestMapping("/api/auths")
@RestController
public class AuthController {

    private final JWTUtil jwtUtil;

    @Operation(summary = "새로운 access Token 발급")
    @PostMapping("/reissue-accessToken")
    public ResponseEntity<Void> reissueAccessToken(
        @CookieValue(value = "refreshToken") String refreshToken,
        HttpServletResponse response
    ) {
        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        String newAccessToken = jwtUtil.createJwt(username, role, 30 * 60 * 1000L); // 새로운 Access Token 생성
        response.addHeader("Authorization", newAccessToken); // Authorization 헤더에 추가

        return ResponseEntity
            .ok()
            .build();
    }
}
