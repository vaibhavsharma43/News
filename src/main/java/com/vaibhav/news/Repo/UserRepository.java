package com.vaibhav.news.Repo;

import com.vaibhav.news.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
   User findByUserName(String userName);
   void deleteByUserName(String userName);


}
