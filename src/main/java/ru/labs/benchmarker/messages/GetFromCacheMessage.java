package ru.labs.benchmarker.messages;

public class GetFromCacheMessage {
    private String url;

    public GetFromCacheMessage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
