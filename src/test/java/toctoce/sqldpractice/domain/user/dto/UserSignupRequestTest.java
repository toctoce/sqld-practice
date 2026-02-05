package toctoce.sqldpractice.domain.user.dto;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserSignupRequestTest {

    @Test
    @DisplayName("올바른 회원가입 데이터가 들어오면 객체가 생성된다.")
    void create_success() {
        assertDoesNotThrow(() ->
                new UserSignupRequest("test@example.com", "Password123!", "닉네임")
        );
    }
}