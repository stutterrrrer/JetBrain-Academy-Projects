package platform;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
public class WebController {
    private static final String CODE =
            "public static void main(String[] args) {\n" +
                    "    SpringApplication.run(CodeSharingPlatform.class, args);\n" +
                    "}";

    @GetMapping(value = "/code", produces = MediaType.TEXT_HTML_VALUE)
    public String getCodeHTML() {
        return "code.html";
    }

    @GetMapping(value = "/api/code", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> getCodeJSON() {
        return Map.of("code", CODE);
    }
}
