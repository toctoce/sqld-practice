package toctoce.sqldpractice.global.security.oauth;

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
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2Attributes attributes = getOAuth2Attributes(userRequest, oAuth2User);
        
        saveOrUpdate(attributes);

        return oAuth2User;
    }

    private static OAuth2Attributes getOAuth2Attributes(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        return OAuth2Attributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());
    }

    private void saveOrUpdate(OAuth2Attributes attributes) {
        AuthProvider provider = attributes.provider();
        String nickname = attributes.nickname();
        String email = attributes.email();

        User user = userRepository.findByProviderAndProviderId(provider, attributes.providerId())
                .map(u -> u.update(nickname, email))
                .orElseGet(() -> User.builder()
                        .nickname(nickname)
                        .email(email)
                        .provider(provider)
                        .providerId(attributes.providerId())
                        .role(UserRole.USER)
                        .build());

        userRepository.save(user);
    }
}
