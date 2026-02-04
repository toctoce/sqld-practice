package toctoce.sqldpractice.domain.user;

import jakarta.persistence.Embeddable;
import toctoce.sqldpractice.global.exception.ErrorCode;
import toctoce.sqldpractice.global.exception.common.InvalidInputException;

@Embeddable
public record Nickname(String nickname) {

    public static Nickname of(String value) {
        validate(value);
        return new Nickname(value);
    }

    public static void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(ErrorCode.INVALID_NICKNAME);
        }
    }
}
