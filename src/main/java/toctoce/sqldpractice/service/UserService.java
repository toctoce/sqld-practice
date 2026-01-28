package toctoce.sqldpractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toctoce.sqldpractice.domain.user.User;
import toctoce.sqldpractice.domain.user.UserRepository;
import toctoce.sqldpractice.domain.user.dto.UserSignupRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(UserSignupRequest request) {
        validateDuplicateEmail(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.of(request, encodedPassword);
        userRepository.save(user);
    }

    private void validateDuplicateEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
                });
    }
}
