package ru.labs.benchmarker;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;

public class BenchmarkerApp {
    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("benchmarker-system");

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        BenchServer server = new BenchServer(system);

    }
}
