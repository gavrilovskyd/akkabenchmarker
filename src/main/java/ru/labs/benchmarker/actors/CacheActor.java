package ru.labs.benchmarker.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;

public class CacheActor extends AbstractActor {
    private HashMap<String, Long> innerStorage = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()

                .build();
    }
}
