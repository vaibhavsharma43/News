package com.vaibhav.news.Repo;

import com.vaibhav.news.Entity.News;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends MongoRepository<News,String> {

}
