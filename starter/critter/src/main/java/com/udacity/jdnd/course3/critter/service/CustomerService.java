package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.User;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer findCustomer(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public void addPetToCustomer(Pet pet, Customer customer) {
        List<Pet> pets = customer.getPets();
        if (pets != null) {
            pets.add(pet);
        } else {
            pets = new ArrayList<Pet>();
            pets.add(pet);
        }
        customer.setPets(pets);
        customerRepository.save(customer);
    }

        /*public User getOwnerByPet(Long petId){
        try {
            return userRepository.findByPet(petId);
        } catch (Exception e){
            return null;
        }
    }*/
}
