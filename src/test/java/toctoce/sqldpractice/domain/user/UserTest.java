package toctoce.sqldpractice.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void 빌더로_유저를_생성할_수_있다() {
        User user = User.builder()
                .email("test@gmail.com")
                .nickname("testuser")
                .role(UserRole.USER)
                .build();

        assertThat(user.getEmail()).isEqualTo("test@gmail.com");
        assertThat(user.getNickname()).isEqualTo("testuser");
        assertThat(user.getRole()).isEqualTo(UserRole.USER);
        assertThat(user.getCreatedAt()).isNotNull();
        assertThat(user.getUpdatedAt()).isNotNull();
    }
}