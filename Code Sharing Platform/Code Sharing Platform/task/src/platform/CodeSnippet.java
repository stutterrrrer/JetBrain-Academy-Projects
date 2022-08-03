package platform;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class CodeSnippet {
    private String code;
    @JsonProperty("date")
    private LocalDateTime dateTime;

    public CodeSnippet(@JsonProperty("code") String code) {
        this.code = code;
        this.dateTime = LocalDateTime.now();
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
