package com.kameleoon.domain.base;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.kameleoon.util.DateProcessor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Objects;

@MappedSuperclass
public class AbstractEntity implements Serializable, Comparable<AbstractEntity> {

    private static Comparator<AbstractEntity> COMPARATOR_BY_ID = Comparator.comparing(AbstractEntity::getId);


    @Column(updatable = false)
    protected Long id;

    @Version
    protected int version;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateProcessor.DATE_FORMAT)
    @Column(name = "createdDate", nullable = false)
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
    protected LocalDateTime updated;


    public AbstractEntity() {
        this.updated = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getUpdated() {
        return this.updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        var entity = (AbstractEntity) o;

        return Objects.equals(this.id, entity.id);
    }


    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }


    @Override
    public String toString() {
        return String.format("AbstractEntity[id='%d%n', version='%d', createdDate='%s%n']",
                this.id, version, DateProcessor.toString(this.updated));
    }


    @Override
    public int compareTo(AbstractEntity o) {
        return this.compareTo(o);
    }
}
