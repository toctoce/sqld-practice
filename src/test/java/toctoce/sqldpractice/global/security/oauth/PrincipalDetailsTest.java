package toctoce.sqldpractice.global.security.oauth;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import toctoce.sqldpractice.domain.user.Nickname;
import toctoce.sqldpractice.domain.user.User;
import toctoce.sqldpractice.domain.user.UserRole;

class PrincipalDetailsTest {

    @Test
    @DisplayName("유저 엔티티로부터 권한 정보와 식별자를 정확히 가져온다.")
    void principal_details_logic() {
        // Given
        User user = User.builder()
                .nickname(Nickname.of("테스터"))
                .providerId("google_12345")
                .role(UserRole.USER)
                .build();
        Map<String, Object> attributes = Map.of("sub", "google_12345");
        PrincipalDetails principalDetails = new PrincipalDetails(user, attributes);

        // When & Then
        assertThat(principalDetails.getName()).isEqualTo("google_12345"); // 대응 완료
        assertThat(principalDetails.getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .containsExactly("USER");
        assertThat(principalDetails.getAttributes()).isEqualTo(attributes);
    }
}