package toctoce.sqldpractice.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 유저_삭제_테스트() {
        User user = createTestUser();
        userRepository.save(user);

        userRepository.delete(user);
        assertThat(userRepository.count()).isEqualTo(0);
    }

    @Test
    void 이메일로_유저_조회가_가능하다() {
        User user = createTestUser();

        userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findByEmail("test@gmail.com");

        // Then
        assertThat(foundUser).isPresent(); // Optional 검증
        assertThat(foundUser.get().getEmail()).isEqualTo("test@gmail.com");
        assertThat(foundUser.get().getNickname()).isEqualTo("test-user");
    }

    private static User createTestUser() {
        User user = User.builder()
                .email("test@gmail.com")
                .nickname("test-user")
                .role(UserRole.USER)
                .build();
        return user;
    }
}