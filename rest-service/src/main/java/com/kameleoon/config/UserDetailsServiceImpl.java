package com.kameleoon.config;

import com.kameleoon.domain.visitor.Visitor;
import com.kameleoon.repository.visitor_repo.VisitorRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private VisitorRepo visitorRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Visitor visitor = visitorRepo.findByUsername(username);
        if (visitor == null) {
            throw new UsernameNotFoundException(String.format("Visitor with username: %s not found", username));
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (visitor.isAdmin()) {
            authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        } else {
            authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(visitor.getName(), visitor.getPassword(), authorities);
        return userDetails;
    }
}
