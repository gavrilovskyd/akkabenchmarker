package ru.labs.benchmarker;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.model.*;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.corba.se.idl.constExpr.Not;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import ru.labs.benchmarker.actors.CacheActor;
import ru.labs.benchmarker.messages.BenchRequest;
import ru.labs.benchmarker.messages.BenchResult;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class BenchServer {
    private static final String URL_PARAMETER_NAME = "url";
    private static final String COUNT_PARAMETER_NAME = "count";
    private static final String COUNT_DEFAULT_VALUE = "10";
    private static final Duration TIMEOUT = Duration.ofMillis(5000);

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final AsyncHttpClient httpClient = Dsl.asyncHttpClient();

    private ActorRef cache;

    public BenchServer(ActorSystem system) {
        this.cache = system.actorOf(Props.create(CacheActor.class), "cache");
    }

    public Flow<HttpRequest, HttpResponse, NotUsed> flow(ActorSystem system, ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class)
                .map(httpRequest -> {
                    String urlParam = httpRequest.getUri().query().getOrElse(URL_PARAMETER_NAME, "");
                    int countParam = Integer.parseInt(
                            httpRequest.getUri().query().getOrElse(COUNT_PARAMETER_NAME, COUNT_DEFAULT_VALUE)
                    );

                    return new BenchRequest(urlParam, countParam);
                })
                .mapAsync(1, benchRequest ->  //TODO: check parallelism parameter
                    Patterns.ask(cache, benchRequest, TIMEOUT)
                            .thenCompose(resp -> {
                                BenchResult cacheResp = ((BenchResult) resp);
                                if (cacheResp.getResponseTime() != -1) {
                                    return CompletableFuture.completedFuture(cacheResp.getResponseTime());
                                }

                                //TODO: add create flow logic
                                return CompletableFuture.completedFuture(0L);
                            })
                )
                .map(benchResult -> {
                    cache.tell(benchResult, ActorRef.noSender());
                    return HttpResponse.create()
                            .withStatus(StatusCodes.OK)
                            .withEntity(
                                    HttpEntities.create(
                                            ContentTypes.APPLICATION_JSON,
                                            jsonMapper.writeValueAsBytes(benchResult)
                                    )
                            );
                });
    }

    private Sink<BenchRequest, CompletionStage<BenchResult>> benchSink() {
         Flow<BenchRequest, Long, NotUsed> timeTestFlow =
                 Flow
                .<BenchRequest>create()
                .mapConcat(benchRequest ->
                    Collections.nCopies(benchRequest.getCount(), benchRequest.getURL())
                )
                .mapAsync(1, url -> {
                    long start = System.nanoTime();
                    return httpClient
                            .prepareGet(url)
                            .execute()
                            .toCompletableFuture()
                            .thenCompose(response ->
                                    CompletableFuture.completedFuture(System.nanoTime() - start));
                });

    }
}
