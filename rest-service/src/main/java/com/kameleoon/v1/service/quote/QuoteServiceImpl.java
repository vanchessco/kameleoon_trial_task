package com.kameleoon.v1.service.quote;


import com.kameleoon.domain.quote.Quote;
import com.kameleoon.domain.visitor.Visitor;
import com.kameleoon.domain.vote.Vote;
import com.kameleoon.exception.QuoteException;
import com.kameleoon.repository.quote_repo.QuoteRepo;
import com.kameleoon.repository.visitor_repo.VisitorRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private QuoteRepo quoteRepo;
    private VisitorRepo visitorRepo;


    @Transactional(readOnly = true)
    @Override
    public Iterable<Quote> findTopTen() {
        return quoteRepo.findTopTen();
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Quote> findWorseTen() {
        return quoteRepo.findWorseTen();
    }

    @Transactional(readOnly = true)
    @Override
    public Quote findRandom() {
        return quoteRepo.findRandom();
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Quote> findAll() {
        return quoteRepo.findAll();
    }

    @Override
    public Quote findCreated(Quote quote, Long visitorID) {
        try {
            Quote newQuote = quoteRepo.save(quote);
            Visitor visitor = visitorRepo.findVisitorById(visitorID);
            visitor.addQuoteCreated(newQuote);
            visitorRepo.save(visitor);

            return newQuote;

        } catch (Exception e) {
            throw new QuoteException(HttpStatus.UNPROCESSABLE_ENTITY, e);
        }
    }

    @Override
    public Quote findUpdated(Quote quote, Long quoteID, Long visitorID) {
        Quote found = quoteRepo.findQuoteById(quoteID);
        Visitor visitor = visitorRepo.findVisitorById(visitorID);
        if (found.getAuthor().equals(visitor.getUsername())) {
            found.setContent(quote.getContent());
            found.setUpdated(LocalDateTime.now(ZoneId.of("Europe/Moscow")));
            found.setAuthor(quote.getAuthor());

            Quote newQuote = quoteRepo.save(found);

            return newQuote;
        }


        return found;
    }

    @Override
    public void delete(Long quoteID, Long visitorID) {
        Quote found = quoteRepo.findQuoteById(quoteID);
        Visitor visitor = visitorRepo.findVisitorById(visitorID);
        if (found.getAuthor().equals(visitor.getUsername())) {
            visitor.getQuotesCreated().remove(found);
            quoteRepo.deleteById(quoteID);
        }
    }

    @Override
    public Quote vote(Long quoteID, Long visitorID, String voteRequest) {
        Quote quote = quoteRepo.findQuoteById(quoteID);
        Set<Vote> votedVisitors = quote.getVotes();
        Visitor visitor = visitorRepo.findVisitorById(visitorID);

        boolean flag = votedVisitors.stream().anyMatch(v -> v.getVisitorID().equals(visitor.getId()));
        if (!flag) {
            Vote vote = new Vote();
            vote.setVisitorID(visitor.getId());
            quote.addVote(vote);
            switch (voteRequest) {
                case "up" -> quote.setVoteCount(quote.getVoteCount() + 1);
                case "down" -> quote.setVoteCount(quote.getVoteCount() - 1);
            }
            Quote newQuote = quoteRepo.save(quote);

            return newQuote;
        }

        return quote;
    }


}
