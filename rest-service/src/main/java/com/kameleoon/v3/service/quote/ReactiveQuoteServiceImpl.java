package com.kameleoon.v3.service.quote;

import com.kameleoon.domain.quote.Quote;
import com.kameleoon.domain.visitor.Visitor;
import com.kameleoon.domain.vote.Vote;
import com.kameleoon.repository.quote_repo.QuoteRepo;
import com.kameleoon.repository.visitor_repo.VisitorRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;


@Service
@Transactional
@AllArgsConstructor
public class ReactiveQuoteServiceImpl implements ReactiveQuoteService {

    private QuoteRepo quoteRepo;
    private VisitorRepo visitorRepo;


    @Transactional(readOnly = true)
    @Override
    public Flux<Quote> findTopTen() {
        return Flux.fromIterable(quoteRepo.findTopTen());
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Quote> findWorseTen() {
        return Flux.fromIterable(quoteRepo.findWorseTen());
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Quote> findRandom() {
        return Mono.just(quoteRepo.findRandom());
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Quote> findAll() {
        return Flux.fromIterable(quoteRepo.findAll());
    }

    @Override
    public Mono<Quote> findUpdated(Mono<Quote> quote, Long quoteID, Long visitorID) {

        Quote found = quoteRepo.findQuoteById(quoteID);
        Visitor visitor = visitorRepo.findVisitorById(visitorID);

        return quote.doOnNext(q -> {
            if (found.getAuthor().equals(visitor.getUsername())) {
                found.setContent(q.getContent());
                found.setUpdated(LocalDateTime.now(ZoneId.of("Europe/Moscow")));
                found.setAuthor(q.getAuthor());
                quoteRepo.save(found);
            }
        }).then(Mono.empty());
    }

    @Override
    public Mono<Void> delete(Long quoteID, Long visitorID) {
        Quote found = quoteRepo.findQuoteById(quoteID);
        Visitor visitor = visitorRepo.findVisitorById(visitorID);
        if (found.getAuthor().equals(visitor.getUsername())) {
            visitor.getQuotesCreated().remove(found);
            quoteRepo.deleteById(quoteID);
        }
        return Mono.empty();
    }

    @Override
    public Mono<Quote> vote(Long quoteID, Long visitorID, String voteRequest) {
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

            return Mono.just(newQuote);
        }

        return Mono.empty();
    }
}
