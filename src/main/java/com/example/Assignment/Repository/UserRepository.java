package com.example.Assignment.Repository;

import com.example.Assignment.Entities.UserEntities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntities, Integer> {
}
