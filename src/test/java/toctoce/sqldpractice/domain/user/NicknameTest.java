package toctoce.sqldpractice.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import toctoce.sqldpractice.global.exception.common.InvalidInputException;

class NicknameTest {

    @Test
    @DisplayName("올바른 닉네임으로 객체를 생성할 수 있다.")
    void create_nickname_success() {
        String value = "준팍";
        Nickname nickname = Nickname.of(value);
        assertThat(nickname.nickname()).isEqualTo(value);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("닉네임이 비어있거나 공백이면 예외를 던진다.")
    void invalid_nickname(String nickname) {
        assertThatThrownBy(() -> Nickname.of(nickname))
                .isInstanceOf(InvalidInputException.class);
    }
}