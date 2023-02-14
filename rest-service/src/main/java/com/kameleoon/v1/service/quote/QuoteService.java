package com.kameleoon.v1.service.quote;

import com.kameleoon.domain.quote.Quote;

public interface QuoteService {

    Iterable<Quote> findTopTen();

    Iterable<Quote> findWorseTen();

    Quote findRandom();

    Iterable<Quote> findAll();

    Quote findCreated(Quote quote, Long visitorID);

    Quote findUpdated(Quote quote, Long quoteID, Long visitorID);

    void delete(Long quoteID, Long visitorID);

    Quote vote(Long quoteID, Long visitorID, String voteRequest);


}
