package com.kameleoon.v3.controller;

import com.kameleoon.domain.quote.Quote;
import com.kameleoon.v3.service.quote.ReactiveQuoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v3/quote")
@Validated(Quote.QuoteValidation.class)
@Api(value = "SecurityReactiveQuoteController")
@PreAuthorize("isAuthenticated()")
public class SecurityReactiveQuoteController {

    private ReactiveQuoteService quoteService;

    @PatchMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update quote")
    @ApiResponse(message = "Quote was successfully updated", code = 200, response = Quote.class)
    public ResponseEntity<Mono<Quote>> update(@Valid @RequestBody Quote quote, @RequestParam("qID") Long quoteID, @RequestParam("vID") Long visitorID) {
        return new ResponseEntity<>(quoteService.findUpdated(Mono.just(quote), quoteID, visitorID), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/delete")
    @ApiOperation(value = "Delete quote")
    @ApiResponse(message = "Quote was successfully deleted", code = 204)
    public ResponseEntity<Mono<Void>> delete(@RequestParam("qID") Long quoteID, @RequestParam("vID") Long visitorID) {
        return new ResponseEntity<>(quoteService.delete(quoteID, visitorID), HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/vote")
    @ApiOperation(value = "Update vote count")
    @ApiResponse(message = "Vote count was successfully updated", code = 200)
    public ResponseEntity<Mono<Quote>> vote(@RequestParam("qID") Long quoteID,
                                            @RequestParam("vID") Long visitorID,
                                            @RequestParam("vote") String voteRequest) {

        return new ResponseEntity<>(quoteService.vote(quoteID, visitorID, voteRequest), HttpStatus.OK);
    }
}
