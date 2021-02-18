package com.makeitlouder.repositories;

import com.makeitlouder.domain.Gender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends CrudRepository<Gender, Integer> {
    Gender findByName(String name);
}
