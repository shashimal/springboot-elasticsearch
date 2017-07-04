package com.duleendra.repository;

import com.duleendra.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Provide custom repository functions for Elasticsearch operations
 *
 *
 * @author Duleendra Shashimal
 *
 */
public interface UserRepositoryCustom {

    /**
     * Handling the advanced search operations of user search
     *
     * @param query string
     * @return page user object
     */
    Page<User> search(String query);

    /**
     * Handling auto suggestion of user names in the search box
     *
     * @param query
     * @return List of user names
     */
    List<String> getUserNameSuggestions(String query);

}
