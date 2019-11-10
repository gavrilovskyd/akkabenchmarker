package ru.labs.benchmarker;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.japi.Pair;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import ru.labs.benchmarker.actors.CacheActor;

public class BenchServer {
    private static final String URL_PARAMETER_NAME = "url";
    private static final String COUNT_PARAMETER_NAME = "count";
    private static final String COUNT_DEFAULT_VALUE = "10";

    private ActorRef cache;

    public BenchServer(ActorSystem system) {
        this.cache = system.actorOf(Props.create(CacheActor.class), "cache");
    }

    public Flow<HttpRequest, HttpResponse, NotUsed> flow(ActorSystem system, ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class)
                .map(req -> {
                    String urlParam = req.getUri().query().getOrElse(URL_PARAMETER_NAME, "");
                    int countParam = Integer.parseInt(
                            req.getUri().query().getOrElse(COUNT_PARAMETER_NAME, COUNT_DEFAULT_VALUE)
                    );

                    return new Pair<>(urlParam, countParam);
                })
                .mapAsync(params -> {
                    if params.
                });
    }
}
