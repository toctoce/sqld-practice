package toctoce.sqldpractice.domain.user;

import jakarta.persistence.Embeddable;
import org.springframework.security.crypto.password.PasswordEncoder;
import toctoce.sqldpractice.global.exception.ErrorCode;
import toctoce.sqldpractice.global.exception.common.InvalidInputException;

@Embeddable
public record Password(String password) {
    // 영문, 숫자, 특수문자 포함 8자 이상
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

    public static Password encrypt(String rawPassword, PasswordEncoder encoder) {
        validate(rawPassword);
        return new Password(encoder.encode(rawPassword));
    }

    public static Password fromEncoded(String encodedValue) {
        return new Password(encodedValue);
    }

    private static void validate(String rawPassword) {
        if (rawPassword == null || !rawPassword.matches(PASSWORD_REGEX)) {
            throw new InvalidInputException(ErrorCode.INVALID_PASSWORD);
        }
    }
}
