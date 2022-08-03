package platform;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    List<CodeSnippet> codeList = new ArrayList<>();

    @GetMapping("/pizza")
    public String pizzaTest(Model model) {
        List<Pizza> pizzaList = List.of(
                new Pizza("durian", 5),
                new Pizza("cheese", 10)
        );
        model.addAttribute("pizzas", pizzaList);
        return "menu";
    }

    @GetMapping(value = "/code/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String getCodeHTML(@PathVariable int id, Model model) {
        CodeSnippet codeSnippet = getCodeJSON(id);
        model.addAttribute("codeSnippet", codeSnippet);
        return "singleSnippet";
    }

    @GetMapping(value = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String getLatestHTML(Model model) {
        List<CodeSnippet> latest = getTenLatest();
        model.addAttribute("snippets", latest);
        return "latestSnippets";
    }

    @GetMapping(value = "/api/code/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CodeSnippet getCodeJSON(@PathVariable int id) {
        return codeList.get(id - 1);
    }

    @GetMapping(value = "/api/code/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CodeSnippet> getTenLatest() {
        int n = codeList.size();
        List<CodeSnippet> latest = new ArrayList<>();
        for (int i = n - 1; i >= Math.max(0, n - 10); i--) latest.add(codeList.get(i));
        return latest;
    }

    @PostMapping(value = "/api/code/new")
    @ResponseBody
    public ResponseEntity<Object> addCode(@RequestBody CodeSnippet code) {
        codeList.add(code);
        return new ResponseEntity<>(Map.of("id", Integer.toString(codeList.size())), HttpStatus.OK);
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
                                  let json = JSON.stringify(object);
                                  let xhr = new XMLHttpRequest();
                                  xhr.open("POST", '/api/code/new', false)
                                  xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                                  xhr.send(json);
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
