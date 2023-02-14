package com.kameleoon.repository.quote_repo;


import com.kameleoon.domain.quote.Quote;
import com.kameleoon.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuoteRepo extends JpaRepository<Quote, Long> {
    @Query(value = "select * from quotes AS q ORDER BY q.vote_count DESC limit 10", nativeQuery = true)
    Iterable<Quote> findTopTen();

    @Query(value = "select * from quotes AS q ORDER BY q.vote_count ASC limit 10", nativeQuery = true)
    Iterable<Quote> findWorseTen();

    @Query(value = "select * from quotes AS q ORDER BY RANDOM() limit 1", nativeQuery = true)
    Quote findRandom();

    default Quote findQuoteById(Long id) {
        return this.findById(id).orElseThrow(() -> new NotFoundException(Quote.class, id));
    }

}
