package com.makeitlouder.repositories;

import com.makeitlouder.domain.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByName(String name);

    @Query("SELECT pet FROM Pet pet WHERE pet.petStatus = 'AVAILABLE'")
    Page<Pet> getAvailablePets(Pageable page);
}
