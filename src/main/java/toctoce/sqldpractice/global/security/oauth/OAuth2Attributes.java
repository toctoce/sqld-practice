package toctoce.sqldpractice.global.security.oauth;

import java.util.Map;
import lombok.Builder;
import toctoce.sqldpractice.domain.user.AuthProvider;
import toctoce.sqldpractice.domain.user.User;
import toctoce.sqldpractice.domain.user.UserRole;
import toctoce.sqldpractice.global.exception.common.InvalidInputException;

@Builder
public record OAuth2Attributes(Map<String, Object> attributes,
                               String nameAttributeKey,
                               String nickname,
                               String email,
                               AuthProvider provider,
                               String providerId) {

    public static OAuth2Attributes of(String registrationId,
                                      String userNameAttributeName,
                                      Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        throw new InvalidInputException();
    }

    private static OAuth2Attributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .provider(AuthProvider.GOOGLE)
                .providerId((String) attributes.get(userNameAttributeName))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .provider(provider)
                .providerId(providerId)
                .role(UserRole.USER)
                .build();
    }
}