package com.kameleoon.v2.service.quote;

import com.kameleoon.domain.quote.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableQuoteService {

    Page<Quote> findAll(Pageable pageable);

}
