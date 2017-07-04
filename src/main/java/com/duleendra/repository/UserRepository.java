package com.duleendra.repository;

import com.duleendra.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * User related predefined elasticsearch operations are extended from the ElasticsearchRepository
 * For any specific function are handled from the UserRepositoryCustom repository
 *
 * @author Duleendra Shashimal
 *
 */
public interface UserRepository extends ElasticsearchRepository<User, String >, UserRepositoryCustom {
}
