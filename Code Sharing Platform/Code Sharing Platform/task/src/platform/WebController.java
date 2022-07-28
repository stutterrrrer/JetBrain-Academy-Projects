package platform;


import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Data
@Controller
public class WebController {
    private static final String INIT_CODE_STR =
            """
                    public static void main(String[] args) {
                        SpringApplication.run(CodeSharingPlatform.class, args);
                    }""";
    private Code code = new Code(INIT_CODE_STR);

    @GetMapping(value = "/code", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getCodeHTML() {
        return
                """
                        <html>
                        <head>
                            <title>Code</title>
                        </head>
                        <body>
                        <pre id="code_snippet">
                        """
                        + code.getCode()
                        + "</pre>"
                        + "<span id=\"load_date\">"
                        + code.getDateTime()
                        + """
                        </span>
                        </body>
                        </html>"""
                ;
    }

    @GetMapping(value = "/api/code", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Code getCodeJSON() {
        return code;
    }

    @PostMapping(value = "/api/code/new")
    @ResponseBody
    public ResponseEntity<Object> updateCode(@RequestBody Code updatedCode) {
        this.code = updatedCode;
        return new ResponseEntity<>(new EmptyJson(), HttpStatus.OK);
    }

    @GetMapping(value = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String httpForm() {
        return
                """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                        	<meta charset="UTF-8">
                        	<title>Create</title>
                        	<script type=javascript>
                              function send() {
                                  let object = {
                                      "code": document.getElementById("code_snippet").value
                                  };
                                 \s
                                  let json = JSON.stringify(object);
                                 \s
                                  let xhr = new XMLHttpRequest();
                                  xhr.open("POST", '/api/code/new', false)
                                  xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                                  xhr.send(json);
                                 \s
                                  if (xhr.status != 200) {
                                    alert("something went wrong");
                                  }
                              }}
                        	</script>
                        </head>
                        <body>
                        <form>
                        		<textarea id="code_snippet">
                        		</textarea>
                        	<button id="send_snippet" type="submit" onclick="send()"> Submit
                        	</button>
                        </form>
                        </body>
                        </html>""";
    }
}
