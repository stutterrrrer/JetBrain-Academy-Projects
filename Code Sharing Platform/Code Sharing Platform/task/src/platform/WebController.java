package platform;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
public class WebController {
    private static final String CODE =
            "public static void main(String[] args) {\n" +
                    "    SpringApplication.run(CodeSharingPlatform.class, args);\n" +
                    "}";

    @GetMapping("/code")
    public String getCodeHTML(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        return "code.html";
    }

    @GetMapping("/api/code")
    @ResponseBody
    public Map<String, String> getCodeJSON(HttpServletResponse response) {
        response.addHeader("Content-Type", "application/json");
        return Map.of("code", CODE);
    }
}
