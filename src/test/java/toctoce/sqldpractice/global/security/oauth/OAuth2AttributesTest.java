package toctoce.sqldpractice.global.security.oauth;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toctoce.sqldpractice.domain.user.AuthProvider;
import toctoce.sqldpractice.domain.user.Email;
import toctoce.sqldpractice.domain.user.Nickname;
import toctoce.sqldpractice.domain.user.User;
import toctoce.sqldpractice.domain.user.UserRole;

class OAuth2AttributesTest {

    @Test
    @DisplayName("구글 속성 맵을 OAuth2Attributes 객체로 변환할 수 있다.")
    void of_google_success() {
        // Given
        Map<String, Object> attributes = Map.of(
                "sub", "google_12345",
                "name", "테스터",
                "email", "test@google.com"
        );

        // When
        OAuth2Attributes result = OAuth2Attributes.of("google", "sub", attributes);

        // Then
        assertThat(result.provider()).isEqualTo(AuthProvider.GOOGLE);
        assertThat(result.providerId()).isEqualTo("google_12345");
        assertThat(result.nickname().nickname()).isEqualTo("테스터");
        assertThat(result.email().email()).isEqualTo("test@google.com");
    }

    @Test
    @DisplayName("OAuth2Attributes를 User 엔티티로 변환할 수 있다.")
    void to_entity_success() {
        // Given
        OAuth2Attributes attributes = OAuth2Attributes.builder()
                .nickname(Nickname.of("테스터"))
                .email(Email.of("test@google.com"))
                .provider(AuthProvider.GOOGLE)
                .providerId("google_12345")
                .build();

        // When
        User user = attributes.toEntity();

        // Then
        assertThat(user.getNickname().nickname()).isEqualTo("테스터");
        assertThat(user.getEmail().email()).isEqualTo("test@google.com");
        assertThat(user.getProvider()).isEqualTo(AuthProvider.GOOGLE);
        assertThat(user.getProviderId()).isEqualTo("google_12345");
        assertThat(user.getRole()).isEqualTo(UserRole.USER);
    }
}