package com.study.BlogPlatform.repo;

import com.study.BlogPlatform.models.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Long> {
}
