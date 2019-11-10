package ru.labs.benchmarker.messages;

public class BenchRequest {
    private String url;
    private Integer count;

    public BenchRequest(String url, Integer count) {
        this.url = url;
        this.count = count;
    }

    public String getURL() {
        return url;
    }

    public Integer getCount() {
        return count;
    }
}
