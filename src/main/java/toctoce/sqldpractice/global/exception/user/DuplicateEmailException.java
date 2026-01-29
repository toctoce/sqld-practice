package toctoce.sqldpractice.global.exception.user;

import toctoce.sqldpractice.global.exception.BusinessException;
import toctoce.sqldpractice.global.exception.ErrorCode;

public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
