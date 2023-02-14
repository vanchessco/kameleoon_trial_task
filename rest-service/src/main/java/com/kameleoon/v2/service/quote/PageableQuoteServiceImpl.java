package com.kameleoon.v2.service.quote;


import com.kameleoon.domain.quote.Quote;
import com.kameleoon.v2.repository.PageableQuoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PageableQuoteServiceImpl implements PageableQuoteService {

    private PageableQuoteRepository quoteRepo;


    @Override
    public Page<Quote> findAll(Pageable pageable) {
        return quoteRepo.findAll(pageable);
    }

}
