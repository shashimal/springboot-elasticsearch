package com.duleendra.service;

import com.duleendra.exception.AppServiceException;
import com.duleendra.model.User;
import com.duleendra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/** Implementation of user service functions
 *
 * @author Duleendra Shashimal
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public Page<User> getUsers(Pageable pageable) throws AppServiceException  {
        try {
            if (pageable == null) {
                throw new AppServiceException("Pageable should not be null");
            }
            return userRepository.findAll(pageable);
        } catch (Exception e) {
            throw new AppServiceException("Error in get users");
        }
    }

    @Override
    public User findUserById(String id) {
        User user = userRepository.findOne(id);
        return user;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public Page<User> search(String query) {
        Page user = userRepository.search(query);
        return user;
    }

    @Override
    public List<String> getUserNameSuggestions(String query) {
        List<String> nameSuggestions = userRepository.getUserNameSuggestions(query);
        return nameSuggestions;
    }
}
