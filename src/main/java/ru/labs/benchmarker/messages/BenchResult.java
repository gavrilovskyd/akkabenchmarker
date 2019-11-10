package ru.labs.benchmarker.messages;

public class BenchResult {
    private String url;
    private Long responseTime;

    public BenchResult(String url, Long responseTime) {
        this.url = url;
        this.responseTime = responseTime;
    }

    public String getURL() {
        return url;
    }

    public Long getResponseTime() {
        return responseTime;
    }
}
