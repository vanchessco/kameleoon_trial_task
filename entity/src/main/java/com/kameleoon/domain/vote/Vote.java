package com.kameleoon.domain.vote;

import com.kameleoon.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "votes")
public class Vote extends AbstractEntity {

    private Long visitorID;

    public Vote() {
        super();
    }

    public Long getVisitorID() {
        return this.visitorID;
    }

    public void setVisitorID(Long visitor) {
        this.visitorID = visitor;
    }
}
