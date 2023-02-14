package com.kameleoon;

import com.kameleoon.domain.quote.Quote;
import com.kameleoon.domain.visitor.Visitor;
import com.kameleoon.repository.quote_repo.QuoteRepo;
import com.kameleoon.repository.visitor_repo.VisitorRepo;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.stream.IntStream;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableEurekaClient
@AllArgsConstructor
public class RestApp {
    private QuoteRepo quoteRepo;
    private VisitorRepo visitorRepo;
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(RestApp.class);
    }


    @PostConstruct
    public void init() {
        IntStream.range(0, 20).forEach(i -> {
            Quote quote = new Quote();
            quote.setContent(i + "ABCDEFGHIJKLMNO");
            quote.setVoteCount(0);
            quote.setVotes(new HashSet<>());
            quoteRepo.save(quote);
        });

        Visitor visitor = new Visitor();
        visitor.setName("Ivan");
        visitor.setUsername("UserIvan");
        visitor.setPassword(passwordEncoder.encode("12345"));
        visitor.setEmail("user@gmail.com");
        visitor.setAdmin(false);

        Visitor visitor1 = new Visitor();
        visitor1.setName("Oleg");
        visitor1.setUsername("UserOleg");
        visitor1.setPassword(passwordEncoder.encode("12345"));
        visitor1.setEmail("user1@gmail.com");
        visitor1.setAdmin(false);


        visitorRepo.save(visitor);
        visitorRepo.save(visitor1);
    }
}
