package toctoce.sqldpractice.domain.user.dto;

public record UserSignupRequest(String email, String password, String nickname) {

    public UserSignupRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}