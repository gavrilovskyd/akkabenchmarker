package ru.labs.benchmarker.messages;

public class BenchRequest {
    private String url;
    private Integer count;

    public BenchRequest(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
