package toctoce.sqldpractice.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toctoce.sqldpractice.global.common.BaseTimeEntity;

@Entity
@Getter
@Table(name = "USERS", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_user_provider_provider_id",
                columnNames = {"provider", "provider_id"}
        )
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    @Embedded
    private Email email;
    @Embedded
    private Password password;
    @Column(nullable = false)
    @Embedded
    private Nickname nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;
    private String providerId;

    @Builder
    public User(Email email,
                Password password,
                Nickname nickname,
                UserRole role,
                AuthProvider provider,
                String providerId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static User of(Email email, Nickname nickname, Password password) {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(UserRole.USER)
                .provider(AuthProvider.LOCAL)
                .build();
    }

    public User update(Nickname nickname, Email email) {
        this.nickname = nickname;
        this.email = email;
        return this;
    }
}