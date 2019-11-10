package ru.labs.benchmarker;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.util.concurrent.CompletionStage;

public class BenchmarkerApp {
    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("benchmarker-system");

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        BenchServer server = new BenchServer(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> httpFlow = server.flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                httpFlow, ConnectHttp.toHost("localhost", 8080), materializer);

        System.out.println("Server started at http://localhost:8080/");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }
}
