package toctoce.sqldpractice.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import toctoce.sqldpractice.global.exception.common.InvalidInputException;

class EmailTest {

    @ParameterizedTest
    @ValueSource(strings = {"Test@Google.com", "USER@DOMAIN.ORG", "MixedCase@Test.Kr"})
    @DisplayName("이메일은 소문자로 정규화되어 저장된다.")
    void email_normalized_to_lowercase(String value) {
        Email email = Email.of(value);
        assertThat(email.email()).isEqualTo(value.toLowerCase(Locale.ROOT));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test@google.com", "user.name@service.kr", "valid_123@domain.org"})
    @DisplayName("올바른 이메일 형식으로 객체를 생성할 수 있다.")
    void create_email_success(String value) {
        Email email = Email.of(value);
        assertThat(email.email()).isEqualTo(value.toLowerCase(Locale.ROOT));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "abc", "test@", "@google.com", "test@.com", " test@google.com",
            "test@google.com ", "test ..test@google.com", ".test@google.com",
            "test@test@google.com", "test@google", "test@google..com", "test@."
    })
    @DisplayName("잘못된 이메일 형식은 InvalidInputException을 던진다.")
    void invalid_email(String email) {
        assertThatThrownBy(() -> Email.of(email))
                .isInstanceOf(InvalidInputException.class);
    }
}