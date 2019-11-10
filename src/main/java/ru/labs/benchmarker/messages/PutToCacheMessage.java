package ru.labs.benchmarker.messages;

public class PutToCacheMessage {
    private String url;
    private Long responseTime;

    public PutToCacheMessage(String url, Long responseTime) {
        this.url = url;
        this.responseTime = responseTime;
    }
    
    public String getURL() {
        return url;
    }


}
