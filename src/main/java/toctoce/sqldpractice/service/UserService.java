package toctoce.sqldpractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toctoce.sqldpractice.domain.user.User;
import toctoce.sqldpractice.domain.user.UserRepository;
import toctoce.sqldpractice.domain.user.dto.UserSignupRequest;
import toctoce.sqldpractice.global.exception.BusinessException;
import toctoce.sqldpractice.global.exception.ErrorCode;

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
                    throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
                });
    }
}
