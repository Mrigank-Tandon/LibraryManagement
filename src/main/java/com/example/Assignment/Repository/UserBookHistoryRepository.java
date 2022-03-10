package com.example.Assignment.Repository;

import com.example.Assignment.Entities.UserBookHistoryEntities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookHistoryRepository extends CrudRepository<UserBookHistoryEntities,Integer> {
}
