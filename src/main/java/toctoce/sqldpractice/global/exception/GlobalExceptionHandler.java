package toctoce.sqldpractice.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import toctoce.sqldpractice.global.exception.common.InvalidInputException;
import toctoce.sqldpractice.global.exception.user.DuplicateEmailException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public String handleBusinessException(BusinessException e, Model model) {
        ErrorCode errorCode = e.getErrorCode();
        model.addAttribute("errorMessage", errorCode.getMessage());

        log.error(errorCode.getMessage());
        return "error";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEmailException.class)
    public String handleDuplicateEmailException(DuplicateEmailException e, Model model) {
        ErrorCode errorCode = e.getErrorCode();
        model.addAttribute("errorMessage", errorCode.getMessage());

        log.error(errorCode.getMessage());
        return "signup";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInputException.class)
    public String handleInvalidInputException(InvalidInputException e, Model model) {
        ErrorCode errorCode = e.getErrorCode();
        model.addAttribute("errorMessage", errorCode.getMessage());

        log.error(errorCode.getMessage());
        return "signup";
    }

}