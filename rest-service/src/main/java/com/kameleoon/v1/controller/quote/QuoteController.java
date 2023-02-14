package com.kameleoon.v1.controller.quote;

import com.kameleoon.domain.quote.Quote;
import com.kameleoon.v1.service.quote.QuoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/quote")
@Validated(Quote.QuoteValidation.class)
@Api(value = "PageableQuoteController")
public class QuoteController {

    private QuoteService quoteService;


    @GetMapping(path = "/top_ten", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds top 10 quotes by votes")
    @ApiResponse(message = "All quotes were successfully found", code = 200)
    public ResponseEntity<Iterable<Quote>> getTopTen() {
        return new ResponseEntity<>(quoteService.findTopTen(), HttpStatus.OK);
    }

    @GetMapping(path = "/worse_ten", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds worse 10 quotes by votes")
    @ApiResponse(message = "All quotes were successfully found", code = 200)
    public ResponseEntity<Iterable<Quote>> getWorseTen() {
        return new ResponseEntity<>(quoteService.findWorseTen(), HttpStatus.OK);
    }

    @GetMapping(path = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds random quote")
    @ApiResponse(message = "Quote was successfully found", code = 200)
    public ResponseEntity<Quote> getRandom() {
        return new ResponseEntity<>(quoteService.findRandom(), HttpStatus.OK);
    }


    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds all quotes")
    @ApiResponse(message = "All quotes were successfully found", code = 200)
    public ResponseEntity<Iterable<Quote>> getAll() {
        return new ResponseEntity<>(quoteService.findAll(), HttpStatus.OK);
    }

}
