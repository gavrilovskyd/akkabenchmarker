package ru.labs.benchmarker.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import ru.labs.benchmarker.messages.BenchRequest;
import ru.labs.benchmarker.messages.BenchResult;

import java.util.HashMap;

public class CacheActor extends AbstractActor {
    private HashMap<String, Long> innerStorage = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(BenchResult.class m -> {
                    
                })
                .match(BenchRequest.class, m -> {
                    Long result = innerStorage.getOrDefault(m.getURL(), -1L);
                    getSender().tell(new BenchResult(m.getURL(), result), getSelf());
                })
                .build();
    }
}
