package Board.Juyoung.service;

import Board.Juyoung.entity.Member;
import Board.Juyoung.repository.MemberRepository;
import Board.Juyoung.service.dto.CustomOAuth2User;
import Board.Juyoung.service.dto.response.GoogleResponse;
import Board.Juyoung.service.dto.response.NaverResponse;
import Board.Juyoung.service.dto.response.OAuth2Response;
import Board.Juyoung.service.dto.response.UserLoginResponse;
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
        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // OAuth provider + id를 username 대신 email 기반으로 저장
        String email = oAuth2Response.getEmail();
        Member existMember = memberRepository.findByEmail(email);

        if (existMember == null) {
            // 신규 회원
            Member newMember = new Member(email, "", oAuth2Response.getName());
            memberRepository.save(newMember);

            UserLoginResponse userDTO = new UserLoginResponse();
            userDTO.setUsername(email);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole("ROLE_USER");

            return new CustomOAuth2User(userDTO);
        } else {
            // 기존 회원 정보 업데이트
            existMember.changeNickname(oAuth2Response.getName());
            existMember.changeEmail(email);
            memberRepository.save(existMember);

            UserLoginResponse userDTO = new UserLoginResponse();
            userDTO.setUsername(email);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole("ROLE_USER");

            return new CustomOAuth2User(userDTO);
        }
    }
}
