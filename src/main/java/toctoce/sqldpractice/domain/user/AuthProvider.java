package toctoce.sqldpractice.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthProvider {
    LOCAL("local"),
    GOOGLE("google"),
    KAKAO("kakao");

    private final String key;
}