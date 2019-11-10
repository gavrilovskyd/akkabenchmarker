package ru.labs.benchmarker;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import ru.labs.benchmarker.actors.CacheActor;

public class BenchServer {
    private ActorRef cache;

    public BenchServer(ActorSystem system) {
        this.cache = system.actorOf(Props.create(CacheActor.class), "cache");
    }

}
