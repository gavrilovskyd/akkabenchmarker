package ru.labs.benchmarker.messages;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TextResponse {
    private String message;

    public TextResponse() {
        this.message = "";
    }

    public TextResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
