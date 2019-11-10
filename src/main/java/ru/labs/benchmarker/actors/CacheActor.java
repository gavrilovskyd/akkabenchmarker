package ru.labs.benchmarker.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import ru.labs.benchmarker.messages.BenchRequest;

import java.util.HashMap;

public class CacheActor extends AbstractActor {
    private HashMap<String, Long> innerStorage = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(BenchRequest.class, m -> {
                    
                })
                .build();
    }
}
