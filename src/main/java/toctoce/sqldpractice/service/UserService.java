package toctoce.sqldpractice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toctoce.sqldpractice.domain.user.Email;
import toctoce.sqldpractice.domain.user.Nickname;
import toctoce.sqldpractice.domain.user.Password;
import toctoce.sqldpractice.domain.user.User;
import toctoce.sqldpractice.domain.user.UserRepository;
import toctoce.sqldpractice.domain.user.dto.UserSignupRequest;
import toctoce.sqldpractice.global.exception.user.DuplicateEmailException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(UserSignupRequest request) {
        Email email = Email.of(request.email());
        validateDuplicateEmail(email);
        Password password = Password.encrypt(request.password(), passwordEncoder);
        User user = User.of(email, Nickname.of(request.nickname()), password);
        userRepository.save(user);
    }

    private void validateDuplicateEmail(Email email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new DuplicateEmailException();
                });
    }
}
