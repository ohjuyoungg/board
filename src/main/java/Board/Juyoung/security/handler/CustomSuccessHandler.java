package Board.Juyoung.security.handler;

import Board.Juyoung.security.jwt.JWTUtil;
import Board.Juyoung.security.user.CustomOAuth2User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    public CustomSuccessHandler(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication)
        throws IOException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String username = customUserDetails.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        String accessToken = jwtUtil.createJwt(username, role, 30 * 60 * 1000L); // 30분
        String refreshToken = jwtUtil.createJwt(username, role, 30 * 24 * 60 * 60 * 1000L); // 30일
        String redirectUrl = "http://yong-2026.s3-website.ap-northeast-2.amazonaws.com/oauth-success?";

        response.sendRedirect(redirectUrl + "accessToken=" + accessToken);
        response.addCookie(createCookie(refreshToken));
    }

    private Cookie createCookie(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        // cookie.setSecure(true); // https만 허용
        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
