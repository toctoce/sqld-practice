package toctoce.sqldpractice.domain.user.dto;

import java.util.regex.Pattern;
import lombok.Getter;

@Getter
//todo : Jackson을 위한 기본 생성자. 필요할 때 넣기.
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupRequest {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    // 영문, 숫자, 특수문자 포함 8자 이상
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");

    private final String email;
    private final String password;
    private final String nickname;

    public UserSignupRequest(String email, String password, String nickname) {
        validate(email, password, nickname);
        this.email = email;
        this.password = password;
        this.nickname = nickname.trim();
    }

    private void validate(String email, String password, String nickname) {
        validateEmail(email);
        validatePassword(password);
        validateNickname(nickname);
    }

    private void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.");
        }
    }

    private void validateNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("닉네임은 필수 입력값입니다.");
        }
    }
}