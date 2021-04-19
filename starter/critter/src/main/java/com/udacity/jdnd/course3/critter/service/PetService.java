package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet){
        Pet returnedPet = petRepository.save(pet);
        Customer customer = returnedPet.getCustomer();

        customer.addPet(returnedPet);
        customerRepository.save(customer);

        return returnedPet;
    }

    public Pet findPet(Long id){
        return petRepository.findById(id).orElse(null);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> findPetsByOwner(Long ownerId){
        return petRepository.findByCustomerId(ownerId);
    }

}
