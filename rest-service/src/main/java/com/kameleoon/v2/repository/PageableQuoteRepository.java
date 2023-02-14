package com.kameleoon.v2.repository;

import com.kameleoon.domain.quote.Quote;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PageableQuoteRepository extends PagingAndSortingRepository<Quote, Long> {

    Iterable<Quote> findAll();
}
