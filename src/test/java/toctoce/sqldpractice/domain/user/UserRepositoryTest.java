package toctoce.sqldpractice.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

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
    void findByEmail() {
        User user = User.builder()
                .email("test@gmail.com")
                .nickname("testuser")
                .role(UserRole.USER)
                .build();

        userRepository.save(user);

        User foundUser = userRepository.findByEmail("test@gmail.com").get();

        assertThat(foundUser.getEmail()).isEqualTo("test@gmail.com");
        assertThat(foundUser.getNickname()).isEqualTo("testuser");
    }
}