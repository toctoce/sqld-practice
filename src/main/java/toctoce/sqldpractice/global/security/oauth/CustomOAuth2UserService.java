package toctoce.sqldpractice.global.security.oauth;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import toctoce.sqldpractice.domain.user.AuthProvider;
import toctoce.sqldpractice.domain.user.User;
import toctoce.sqldpractice.domain.user.UserRepository;
import toctoce.sqldpractice.domain.user.UserRole;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 유저 정보 로드
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 속성 추출
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 유저 정보 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        AuthProvider provider = AuthProvider.findByKey(registrationId);
        String providerId = (String) attributes.get("sub");
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        log.info("registrationId={}, providerId={}, email={}, name={}",
                registrationId, providerId, email, name);

        saveOrUpdate(provider, providerId, email, name);

        return oAuth2User;
    }

    private void saveOrUpdate(AuthProvider provider, String providerId, String nickname, String email) {
        User user = userRepository.findByProviderAndProviderId(provider, providerId)
                .map(u -> u.update(nickname, email))
                .orElseGet(() -> User.builder()
                        .nickname(nickname)
                        .email(email)
                        .provider(provider)
                        .providerId(providerId)
                        .role(UserRole.USER)
                        .build());

        userRepository.save(user);
    }
}
