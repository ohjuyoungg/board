package Board.Juyoung.service.dto.response;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attributes;
    private final Map<String, Object> kakaoAccount;
    private final Map<String, String> profile;

    public KakaoResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.profile = (Map<String, String>) kakaoAccount.get("profile");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return profile.get("nickname");
    }

    @Override
    public String getProfileImage() {
        return profile.get("profile_image_url");
    }

    @Override
    public String getEmail() {
        return kakaoAccount.get("email").toString();
    }
}
