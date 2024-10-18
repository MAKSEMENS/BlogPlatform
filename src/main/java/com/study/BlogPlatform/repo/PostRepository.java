package com.study.BlogPlatform.repo;

import com.study.BlogPlatform.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,Long> {
}
