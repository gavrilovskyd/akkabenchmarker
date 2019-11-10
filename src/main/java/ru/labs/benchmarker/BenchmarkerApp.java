package ru.labs.benchmarker;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;

public class BenchmarkerApp {
    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("benchmarker-system");

        final Http http = Http.get(system);
        final 
    }
}
