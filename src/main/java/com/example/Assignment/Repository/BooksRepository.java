package com.example.Assignment.Repository;

import com.example.Assignment.Entities.BooksEntities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends CrudRepository<BooksEntities, Integer> {
}
