package toctoce.sqldpractice.global.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public String handleBusinessException(BusinessException e, Model model) {
        ErrorCode errorCode = e.getErrorCode();

        model.addAttribute("errorMessage", errorCode.getMessage());

        if (errorCode == ErrorCode.DUPLICATE_EMAIL) {
            return "signup";
        }
        return "error"; // 공통 에러 페이지
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("errorMessage", "시스템에 문제가 발생했습니다.");
        return "error";
    }
}