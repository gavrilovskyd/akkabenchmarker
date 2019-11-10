package ru.labs.benchmarker.messages;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BenchResult {
    private static final long TIME_FACTOR = 1000000; // NANO to MS

    private String url;
    private Long responseTime;

    public BenchResult(String url, Long responseTime) {
        this.url = url;
        this.responseTime = responseTime / TIME_FACTOR;
    }

    public String getURL() {
        return url;
    }

    public Long getResponseTime() {
        return responseTime;
    }
}
