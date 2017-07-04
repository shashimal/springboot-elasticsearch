package com.duleendra.service;

import com.duleendra.exception.AppServiceException;
import com.duleendra.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User related business logic and service functions are handled here
 *
 *
 * @author Duleendra Shashimal
 *
 */
public interface UserService {

    /**
     * Save an user
     *
     * @param user object
     * @return saved user object
     */
    User save(User user);

    /**
     * Get list of users
     *
     * @param pageable object
     * @return page user object
     */
    Page<User> getUsers(Pageable pageable) throws AppServiceException;

    /**
     * Find an user by id
     *
     * @param id
     * @return user object
     */
    User findUserById(String id);

    /**
     * Delete an user
     *
     * @param user object
     *
     */
    void delete(User user);

    /**
     * Advanced search option
     *
     * @param  query
     * @return page user object
     */
    Page<User> search(String query);

    /**
     * Auto suggestion of user names in the search box
     *
     * @param query
     * @return List of user names
     */
    List<String> getUserNameSuggestions(String query);
}
