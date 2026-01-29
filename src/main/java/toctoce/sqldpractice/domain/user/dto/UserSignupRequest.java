package toctoce.sqldpractice.domain.user.dto;

import java.util.regex.Pattern;
import lombok.Getter;
import toctoce.sqldpractice.global.exception.ErrorCode;
import toctoce.sqldpractice.global.exception.common.InvalidInputException;

@Getter
//todo : Jackson을 위한 기본 생성자. 필요할 때 넣기.
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupRequest {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");

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
        validateNickname(nickname);
        validatePassword(password);
    }

    private void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidInputException(ErrorCode.INVALID_EMAIL);
        }
    }

    private void validatePassword(String password) {
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidInputException(ErrorCode.INVALID_PASSWORD);
        }
    }

    private void validateNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new InvalidInputException(ErrorCode.INVALID_NICKNAME);
        }
    }
}