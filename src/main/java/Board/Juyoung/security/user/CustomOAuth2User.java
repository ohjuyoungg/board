package Board.Juyoung.security.user;

import Board.Juyoung.service.dto.response.UserLoginResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

    private final UserLoginResponse userDTO;

    public CustomOAuth2User(UserLoginResponse userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> userDTO.getRole());
        return collection;
    }

    @Override
    public String getName() {
        return userDTO.getName();
    }
}