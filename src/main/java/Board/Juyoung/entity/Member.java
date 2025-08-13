package Board.Juyoung.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 - PK
    private Long id;
    private String loginId;
    private String email;
    private String password;
    private String nickname;
    private String profileImage;

    public Member(String loginId, String email, String password, String nickname, String profileImage) {
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
