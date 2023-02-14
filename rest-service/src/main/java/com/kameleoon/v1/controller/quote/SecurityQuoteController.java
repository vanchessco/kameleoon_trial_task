package com.kameleoon.v1.controller.quote;


import com.kameleoon.domain.quote.Quote;
import com.kameleoon.v1.service.quote.QuoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ResponseHeader;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/quote")
@Validated(Quote.QuoteValidation.class)
@Api(value = "SecurityQuoteController")
@PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_ADMIN')")
public class SecurityQuoteController {
    private QuoteService quoteService;


    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create quote")
    @ApiResponse(
            message = "Quote was successfully created",
            code = 201,
            response = Quote.class,
            responseHeaders = {
                    @ResponseHeader(name = "location", response = URI.class)
            }
    )
    public ResponseEntity<Quote> create(@Valid @RequestBody Quote quote,
                                        @RequestParam("vID") Long visitorID) {

        Quote newQuote = quoteService.findCreated(quote, visitorID);

        HttpHeaders httpHeaders = new HttpHeaders();
        URI newURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newQuote.getId())
                .toUri();
        httpHeaders.setLocation(newURI);


        return new ResponseEntity<>(newQuote, httpHeaders, HttpStatus.CREATED);
    }


    @PatchMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update quote")
    @ApiResponse(message = "Quote was successfully updated", code = 200, response = Quote.class)
    public ResponseEntity<Quote> update(@Valid @RequestBody Quote quote,
                                        @RequestParam("qID") Long quoteID,
                                        @RequestParam("vID") Long visitorID) {
        return new ResponseEntity<>(quoteService.findUpdated(quote, quoteID, visitorID), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete")
    @ApiOperation(value = "Delete quote")
    @ApiResponse(message = "Quote was successfully deleted", code = 204)
    public ResponseEntity<Void> delete(@RequestParam("qID") Long quoteID, @RequestParam("vID") Long visitorID) {
        quoteService.delete(quoteID, visitorID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/vote")
    @ApiOperation(value = "Update vote count")
    @ApiResponse(message = "Vote count was successfully updated", code = 200)
    public ResponseEntity<Quote> vote(@RequestParam("qID") Long quoteID,
                                      @RequestParam("vID") Long visitorID,
                                      @RequestParam("vote") String voteRequest) {
        return new ResponseEntity<>(quoteService.vote(quoteID, visitorID, voteRequest), HttpStatus.OK);
    }


}
