package toctoce.sqldpractice.global.security.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toctoce.sqldpractice.domain.user.AuthProvider;
import toctoce.sqldpractice.domain.user.Email;
import toctoce.sqldpractice.domain.user.Nickname;
import toctoce.sqldpractice.domain.user.User;
import toctoce.sqldpractice.domain.user.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2Attributes attributes = getOAuth2Attributes(userRequest, oAuth2User);

        User user = saveOrUpdate(attributes);

        return new PrincipalDetails(user, oAuth2User.getAttributes());
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

    private User saveOrUpdate(OAuth2Attributes attributes) {
        AuthProvider provider = attributes.provider();
        Nickname nickname = attributes.nickname();
        Email email = attributes.email();

        User user = userRepository.findByEmail(attributes.email())
                .map(u -> {
                    if (!u.getProvider().equals(attributes.provider())) {
                        throw new OAuth2AuthenticationException("이미 " + u.getProvider().name() + " 계정으로 가입된 이메일입니다.");
                    }
                    return u.update(nickname, email);
                })
                .orElseGet(attributes::toEntity);

        userRepository.save(user);
        return user;
    }
}
