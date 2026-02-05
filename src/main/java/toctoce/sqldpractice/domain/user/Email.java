package toctoce.sqldpractice.domain.user;

import jakarta.persistence.Embeddable;
import java.util.Locale;
import toctoce.sqldpractice.global.exception.ErrorCode;
import toctoce.sqldpractice.global.exception.common.InvalidInputException;

@Embeddable
public record Email(String email) {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$";

    public static Email of(String value) {
        String normalized = normalize(value);
        validate(normalized);
        return new Email(normalized);
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        return value.toLowerCase(Locale.ROOT);
    }

    public static void validate(String value) {
        if (value == null || !value.matches(EMAIL_REGEX)) {
            throw new InvalidInputException(ErrorCode.INVALID_EMAIL);
        }
    }
}