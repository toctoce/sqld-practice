package toctoce.sqldpractice.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import toctoce.sqldpractice.domain.user.AuthProvider;
import toctoce.sqldpractice.domain.user.User;
import toctoce.sqldpractice.domain.user.UserRepository;
import toctoce.sqldpractice.domain.user.UserRole;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공 시 로그인 페이지로 리다이렉트된다.")
    void signup_success() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("email", "newuser@gmail.com")
                        .param("password", "Password123!")
                        .param("nickname", "영규")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @DisplayName("이메일이 중복되면 가입에 실패하고 회원가입 페이지로 돌아간다.")
    void signup_fail_duplicate_email() throws Exception {
        // Given: 이미 존재하는 유저 저장
        userRepository.save(User.builder()
                .email("duplicate@gmail.com")
                .password("password123!")
                .nickname("기존유저")
                .role(UserRole.USER)
                .provider(AuthProvider.LOCAL)
                .build());

        // When & Then: 동일한 이메일로 가입 시도
        mockMvc.perform(post("/signup")
                        .param("email", "duplicate@gmail.com")
                        .param("password", "Password123!")
                        .param("nickname", "새유저")
                        .with(csrf()))
                .andExpect(status().isConflict())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @DisplayName("잘못된 이메일 형식으로 가입 시도 시 가입 폼을 다시 보여준다.")
    void signup_fail_invalid_email() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("email", "invalid-email")
                        .param("password", "Password123!")
                        .param("nickname", "영규")
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @DisplayName("비밀번호 정책 위반 시 가입 폼을 다시 보여준다.")
    void signup_fail_invalid_password() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("email", "test@gmail.com")
                        .param("password", "short")
                        .param("nickname", "영규")
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @DisplayName("잘못된 닉네임 입력 시 가입 폼을 다시 보여준다.")
    void signup_fail_invalid_nickname() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("email", "test@gmail.com")
                        .param("password", "Password123!")
                        .param("nickname", " ")
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("errorMessage"));
    }
}