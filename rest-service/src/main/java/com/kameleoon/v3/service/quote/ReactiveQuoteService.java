package com.kameleoon.v3.service.quote;

import com.kameleoon.domain.quote.Quote;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveQuoteService {

    Flux<Quote> findTopTen();

    Flux<Quote> findWorseTen();

    Mono<Quote> findRandom();

    Flux<Quote> findAll();

    Mono<Quote> findUpdated(Mono<Quote> quote, Long quoteID, Long visitorID);

    Mono<Void> delete(Long quoteID, Long visitorID);

    Mono<Quote> vote(Long quoteID, Long visitorID, String voteRequest);
}
