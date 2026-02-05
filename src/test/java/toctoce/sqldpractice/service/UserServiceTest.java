package toctoce.sqldpractice.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import toctoce.sqldpractice.domain.user.Email;
import toctoce.sqldpractice.domain.user.User;
import toctoce.sqldpractice.domain.user.UserRepository;
import toctoce.sqldpractice.domain.user.dto.UserSignupRequest;
import toctoce.sqldpractice.global.exception.user.DuplicateEmailException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("신규 회원은 회원가입에 성공한다.")
    void signup_success() {
        // given
        UserSignupRequest request = new UserSignupRequest("new@test.com", "Password123!", "닉네임");
        given(userRepository.findByEmail(any(Email.class))).willReturn(Optional.empty());
        given(passwordEncoder.encode(anyString())).willReturn("encoded_password");

        // when
        userService.signup(request);

        // then
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    @DisplayName("이미 가입된 이메일인 경우 DuplicateEmailException을 던진다.")
    void signup_fail_duplicate_email() {
        // given
        UserSignupRequest request = new UserSignupRequest("dup@test.com", "Password123!", "뉴비");
        given(userRepository.findByEmail(Email.of("dup@test.com"))).willReturn(Optional.of(mock(User.class)));

        // when & then
        assertThatThrownBy(() -> userService.signup(request))
                .isInstanceOf(DuplicateEmailException.class);
    }
}