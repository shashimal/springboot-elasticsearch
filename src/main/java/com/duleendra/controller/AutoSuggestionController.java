package com.duleendra.controller;

import com.duleendra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Name suggestions function is handled here
 *
 *
 * @author Duleendra Shashimal
 *
 */

@RestController
public class AutoSuggestionController {

    @Autowired
    private UserService userService;

    /**
     * Get name suggestions of the users
     *
     * @param query
     * @return list of user names
     */
    @GetMapping(value = "/users/suggestions/{query}")
    public ResponseEntity getUserNameSuggestions(@PathVariable String query) {
        List<String> nameSuggestions = userService.getUserNameSuggestions(query);
        return new ResponseEntity(nameSuggestions, HttpStatus.OK);
    }
}
