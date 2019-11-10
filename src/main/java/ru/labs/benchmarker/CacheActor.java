package ru.labs.benchmarker;

import akka.actor.AbstractActor;

import java.util.HashMap;

public class CacheActor extends AbstractActor {
    private HashMap<String, Long> innerStorage = new HashMap<>();

    @Override
    public Receive createReceive() {
        
    }
}
