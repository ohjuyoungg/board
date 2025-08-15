package Board.Juyoung.service;

import Board.Juyoung.entity.Member;
import Board.Juyoung.repository.MemberRepository;
import Board.Juyoung.service.dto.CustomOAuth2User;
import Board.Juyoung.service.dto.response.GoogleResponse;
import Board.Juyoung.service.dto.response.KakaoResponse;
import Board.Juyoung.service.dto.response.OAuth2Response;
import Board.Juyoung.service.dto.response.UserLoginResponse;
import java.util.Optional;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;
        if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // OAuth provider + id
        String provider = oAuth2Response.getProvider();
        String providerId = oAuth2Response.getProviderId();
        String loginId = provider + "-" + providerId;

        Optional<Member> member = memberRepository.findByLoginId(loginId);

        if (member.isPresent()) {
            // 기존 회원
            Member existingMember = member.get();
            existingMember.changeNickname(oAuth2Response.getName());
            existingMember.changeProfileImage(oAuth2Response.getProfileImage());
            memberRepository.save(existingMember);

            UserLoginResponse userDTO = new UserLoginResponse();
            userDTO.setName(loginId);
            userDTO.setRole("ROLE_USER");

            return new CustomOAuth2User(userDTO);
        } else {
            // 신규 회원
            Member newMember = new Member(loginId, "", "", oAuth2Response.getName(), oAuth2Response.getProfileImage());
            memberRepository.save(newMember);

            UserLoginResponse userDTO = new UserLoginResponse();
            userDTO.setName(loginId);
            userDTO.setRole("ROLE_USER");

            return new CustomOAuth2User(userDTO);
        }
    }
}
