package toctoce.sqldpractice.global.security.oauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.test.util.ReflectionTestUtils;
import toctoce.sqldpractice.domain.user.AuthProvider;
import toctoce.sqldpractice.domain.user.Email;
import toctoce.sqldpractice.domain.user.Nickname;
import toctoce.sqldpractice.domain.user.User;
import toctoce.sqldpractice.domain.user.UserRepository;

@ExtendWith(MockitoExtension.class)
class CustomOAuth2UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomOAuth2UserService oauthService;

    private OAuth2Attributes createGoogleAttributes(String email) {
        return OAuth2Attributes.builder()
                .email(Email.of(email))
                .nickname(Nickname.of("테스터"))
                .provider(AuthProvider.GOOGLE)
                .providerId("google_id_123")
                .build();
    }

    @Test
    @DisplayName("신규 유저라면 회원가입(save)을 진행한다.")
    void scenario_1_registration() {
        // Given
        OAuth2Attributes attributes = createGoogleAttributes("new@test.com");
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        // When
        ReflectionTestUtils.invokeMethod(oauthService, "saveOrUpdate", attributes);

        // Then
        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("같은 제공자의 기존 유저라면 정보를 업데이트한다.")
    void scenario_2_update() {
        // Given
        OAuth2Attributes attributes = createGoogleAttributes("same@test.com");
        User user = User.builder()
                .email(Email.of("same@test.com"))
                .nickname(Nickname.of("옛날이름"))
                .provider(AuthProvider.GOOGLE).build();

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        // When
        ReflectionTestUtils.invokeMethod(oauthService, "saveOrUpdate", attributes);

        // Then
        assertThat(user.getNickname().nickname()).isEqualTo("테스터");
    }

    @Test
    @DisplayName("이메일은 같지만 제공자(Provider)가 다르면 에러를 던진다.")
    void scenario_3_different_provider_error() {
        // Given
        OAuth2Attributes attributes = createGoogleAttributes("duplicate@test.com");
        User user = User.builder()
                .email(Email.of("duplicate@test.com"))
                .provider(AuthProvider.LOCAL).build();

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        // When & Then
        assertThatThrownBy(() ->
                ReflectionTestUtils.invokeMethod(oauthService, "saveOrUpdate", attributes))
                .isInstanceOf(OAuth2AuthenticationException.class);
    }
}