package com.kameleoon.v1.controller.visitor;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/visitor")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class VisitorController {


    @GetMapping
    @ApiOperation(value = "Get authenticated user")
    @ApiResponse(message = "User was successfully found", code = 200)
    public ResponseEntity<User> getAuthenticated() {
        User v = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(v, HttpStatus.OK);
    }

}
