package com.freitas.exemplo1.service;

import com.freitas.exemplo1.model.Pet;
import com.freitas.exemplo1.repository.PetRepository;
import com.freitas.exemplo1.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PetService {
    
    @Autowired
    private PetRepository petRepository;
    
    @Autowired
    private OwnerRepository ownerRepository;

    @Transactional(readOnly = true)
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + id));
    }

    @Transactional
    public Pet createPet(Pet pet) {
        if (pet.getOwner() != null && pet.getOwner().getId() != null) {
            ownerRepository.findById(pet.getOwner().getId())
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        }
        return petRepository.save(pet);
    }

    @Transactional
    public Pet updatePet(Long id, Pet petDetails) {
        Pet pet = getPetById(id);
        pet.setName(petDetails.getName());
        pet.setSpecies(petDetails.getSpecies());
        pet.setBreed(petDetails.getBreed());
        pet.setBirthDate(petDetails.getBirthDate());
        pet.setMedicalHistory(petDetails.getMedicalHistory());
        return petRepository.save(pet);
    }

    @Transactional
    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new EntityNotFoundException("Pet not found with id: " + id);
        }
        petRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Pet> getPetsByOwner(Long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }
}