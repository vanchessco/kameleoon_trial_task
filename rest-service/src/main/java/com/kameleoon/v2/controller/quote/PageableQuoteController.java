package com.kameleoon.v2.controller.quote;

import com.kameleoon.domain.quote.Quote;
import com.kameleoon.v2.service.quote.PageableQuoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/quote")
@Validated(Quote.QuoteValidation.class)
@Api(value = "PageableQuoteController")
public class PageableQuoteController {

    private PageableQuoteService pageableQuoteService;


    // page + size in RequestParam
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds all quotes")
    @ApiResponse(message = "All quotes were successfully found", code = 200)
    public ResponseEntity<Page<Quote>> findAll(Pageable pageable) {
        return new ResponseEntity<>(pageableQuoteService.findAll(pageable), HttpStatus.OK);
    }

}
