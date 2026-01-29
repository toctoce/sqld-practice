package toctoce.sqldpractice.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("errorMessage", "시스템에 문제가 발생했습니다.");
        return "error";
    }
}