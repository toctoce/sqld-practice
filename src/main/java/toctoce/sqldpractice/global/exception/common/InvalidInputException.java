package toctoce.sqldpractice.global.exception.common;

import toctoce.sqldpractice.global.exception.BusinessException;
import toctoce.sqldpractice.global.exception.ErrorCode;

public class InvalidInputException extends BusinessException {

    public InvalidInputException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }

    public InvalidInputException(ErrorCode errorCode) {
        super(errorCode);
    }
}
