package toctoce.sqldpractice.domain.user;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthProvider {
    LOCAL("local"),
    GOOGLE("google"),
    KAKAO("kakao");

    private final String key;

    public static AuthProvider findByKey(String registrationId) {
        return Arrays.stream(AuthProvider.values())
                .filter(provider -> provider.getKey().equals(registrationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 로그인 수단입니다: " + registrationId));
    }
}