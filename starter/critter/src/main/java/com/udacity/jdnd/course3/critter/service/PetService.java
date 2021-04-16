package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    public void eatPet(Pet pet){
        petRepository.save(pet);
    }

    public Pet expelPet(Long id){
        return petRepository.findById(id).orElse(null);
    }

}
