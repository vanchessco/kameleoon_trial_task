package com.kameleoon.repository.visitor_repo;


import com.kameleoon.domain.visitor.Visitor;
import com.kameleoon.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepo extends JpaRepository<Visitor, Long> {
    Visitor findByUsername(String username);

    default Visitor findVisitorById(Long id) {
        return this.findById(id).orElseThrow(() -> new NotFoundException(Visitor.class, id));
    }
}
