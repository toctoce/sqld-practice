package toctoce.sqldpractice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        // 1. 요청으로부터 세션을 가져옵니다.
        HttpSession session = request.getSession(false); // 세션이 없으면 새로 만들지 않음

        if (session != null) {
            // 2. 세션에 저장된 정보를 꺼냅니다.
            // (OAuth2 로그인 시 시큐리티가 'SPRING_SECURITY_CONTEXT'라는 키로 저장함)
            Object securityContext = session.getAttribute("SPRING_SECURITY_CONTEXT");

            if (securityContext != null) {
                // 로그인된 상태로 간주하고 로직 처리
                model.addAttribute("user", "로그인 사용자");
            }
        }

        return "index";
    }
}
