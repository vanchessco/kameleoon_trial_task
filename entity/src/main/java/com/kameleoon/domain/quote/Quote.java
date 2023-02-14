package com.kameleoon.domain.quote;

import com.kameleoon.domain.base.AbstractEntity;
import com.kameleoon.domain.vote.Vote;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "quotes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "content")
})
public class Quote extends AbstractEntity {

    @NotNull(groups = QuoteValidation.class)
    @NotEmpty(groups = QuoteValidation.class)
    @Length(min = 10, max = 100, groups = QuoteValidation.class)
    @Column(nullable = false)
    private String content;
    @NotNull(groups = QuoteValidation.class)
    @NotEmpty(groups = QuoteValidation.class)
    @Length(min = 5, max = 50, groups = QuoteValidation.class)
    @Column(nullable = false)
    private String author;
    private int voteCount;
    private Set<Vote> votes;

    public Quote() {
        super();
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getVoteCount() {
        return this.voteCount;
    }

    public void setVoteCount(int voteCount) {
        if (voteCount >= 0) {
            this.voteCount = voteCount;
        } else {
            throw new IllegalArgumentException(String.format("Arg: %d cannot be < 0", voteCount));
        }
    }

    @OneToMany(cascade = CascadeType.ALL)
    public Set<Vote> getVotes() {
        return this.votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        if (!Objects.equals(this.id, quote.id)) return false;

        return Objects.equals(this.content, quote.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), content);
    }

    public interface QuoteValidation {
    }

}
