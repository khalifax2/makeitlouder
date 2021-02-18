package com.makeitlouder.repositories;

import com.makeitlouder.domain.PetStatus;
import org.springframework.data.repository.CrudRepository;

public interface PetStatusRepository extends CrudRepository<PetStatus, Integer> {
    PetStatus findByName(String name);
}
