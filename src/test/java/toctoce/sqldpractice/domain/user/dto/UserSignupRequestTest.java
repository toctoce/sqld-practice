package toctoce.sqldpractice.domain.user.dto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import toctoce.sqldpractice.global.exception.common.InvalidInputException;

class UserSignupRequestTest {

    @Test
    @DisplayName("올바른 회원가입 데이터가 들어오면 객체가 생성된다.")
    void create_success() {
        assertDoesNotThrow(() ->
                new UserSignupRequest("test@example.com", "Password123!", "닉네임")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "abc",
            "test@",
            "@google.com",
            "test@.com",
            " test@google.com",
            "test@google.com ",
            "test @google.com",
            "test..test@google.com",
            ".test@google.com",
            "test@test@google.com",
            "test@google",
            "test@google..com",
            "test@.",
            ""
    })
    @DisplayName("잘못된 이메일 형식은 InvalidInputException을 던진다.")
    void invalid_email(String email) {
        assertThatThrownBy(() -> new UserSignupRequest(email, "Password123!", "닉네임"))
                .isInstanceOf(InvalidInputException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"short1!", "nonumber!", "noemoticon123", "12345!!!!!"})
    @DisplayName("비밀번호 정책(8자 이상, 영문+숫자+특수문자) 위반 시 예외를 던진다.")
    void invalid_password(String password) {
        assertThatThrownBy(() -> new UserSignupRequest("test@test.com", password, "닉네임"))
                .isInstanceOf(InvalidInputException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("닉네임이 비어있거나 공백이면 예외를 던진다.")
    void invalid_nickname(String nickname) {
        assertThatThrownBy(() -> new UserSignupRequest("test@test.com", "Password123!", nickname))
                .isInstanceOf(InvalidInputException.class);
    }
}