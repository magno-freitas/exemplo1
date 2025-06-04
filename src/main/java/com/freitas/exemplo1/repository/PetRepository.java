package com.freitas.exemplo1.repository;

import com.freitas.exemplo1.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwnerId(Long ownerId);
    List<Pet> findByOwnerEmail(String email);
}