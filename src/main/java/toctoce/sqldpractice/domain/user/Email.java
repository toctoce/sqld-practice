package toctoce.sqldpractice.domain.user;

import jakarta.persistence.Embeddable;
import toctoce.sqldpractice.global.exception.ErrorCode;
import toctoce.sqldpractice.global.exception.common.InvalidInputException;

@Embeddable
public record Email(String email) {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$";


    public static Email of(String value) {
        validate(value);
        return new Email(value);
    }

    public static void validate(String value) {
        if (value == null || !value.matches(EMAIL_REGEX)) {
            throw new InvalidInputException(ErrorCode.INVALID_EMAIL);
        }
    }
}