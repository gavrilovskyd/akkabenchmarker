package ru.labs.benchmarker.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import ru.labs.benchmarker.messages.BenchRequest;
import ru.labs.benchmarker.messages.BenchResult;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CacheActor extends AbstractActor {
    private HashMap<String, Long> innerStorage = new HashMap<>();
    private LinkedHashMap<String, Long> lruCache;

    public CacheActor(int cacheSize) {
        this.lruCache = new LinkedHashMap<String, Long>(cacheSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(java.util.Map.Entry<String, Long> eldest) {
                return this.size() > cacheSize;
            }
        };
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(BenchResult.class, m -> {
                    innerStorage.put(m.getURL(), m.getResponseTime());
                })
                .match(BenchRequest.class, m -> {
                    Long result = innerStorage.getOrDefault(m.getURL(), -1L);
                    getSender().tell(new BenchResult(m.getURL(), result), getSelf());
                })
                .build();
    }
}
