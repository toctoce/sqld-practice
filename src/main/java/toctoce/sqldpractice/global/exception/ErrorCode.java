package toctoce.sqldpractice.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C01", "올바르지 않은 입력값입니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C02", "찾을 수 없는 리소스입니다."),
    // user
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U01", "이미 존재하는 이메일입니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "U02", "올바른 이메일 형식이 아닙니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "U03", "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다."),
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST, "U04", "닉네임은 필수 입력값입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}