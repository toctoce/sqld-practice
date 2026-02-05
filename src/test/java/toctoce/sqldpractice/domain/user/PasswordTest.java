package toctoce.sqldpractice.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import toctoce.sqldpractice.global.exception.common.InvalidInputException;

class PasswordTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("정책에 맞는 비밀번호는 암호화되어 저장된다.")
    void encrypt_password_success() {
        String raw = "Password123!";
        Password password = Password.encrypt(raw, passwordEncoder);

        // 평문이 그대로 저장되지 않았는지 확인
        assertThat(password.password()).isNotEqualTo(raw);
        // 인코더를 통해 일치 여부 확인
        assertThat(passwordEncoder.matches(raw, password.password())).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"short1!", "nonumber!", "noemoticon123", "12345!!!!!"})
    @DisplayName("비밀번호 정책 위반 시 암호화 전에 예외를 던진다.")
    void invalid_password(String password) {
        assertThatThrownBy(() -> Password.encrypt(password, passwordEncoder))
                .isInstanceOf(InvalidInputException.class);
    }

    @Test
    @DisplayName("이미 암호화된 값으로 객체를 생성할 때는 검증하지 않는다.")
    void create_from_encoded() {
        String encoded = "already_encoded_value";
        Password password = Password.fromEncoded(encoded);
        assertThat(password.password()).isEqualTo(encoded);
    }
}