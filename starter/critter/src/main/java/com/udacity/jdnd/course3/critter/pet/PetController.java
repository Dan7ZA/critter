package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        // Create a new pet object & convert the dto to customer in order to save it.
        Pet newPet = this.petService.savePet(this.convertPetDTOToPet(petDTO));
        // Update customer with newly created ID
        petDTO.setId(newPet.getId());
        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        //Initialise employee object from DB
        Pet pet = this.petService.findPet(petId);
        //Convert to employee object to employeeDTO & return
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        //Retrieve list of all pets & covert to DTO
        List<Pet> petList = this.petService.getAllPets();

        List<PetDTO> petDTO = petList.stream()
                .map(PetController::convertPetToPetDTO)
                .collect(Collectors.toList());
        return petDTO;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        //Get list of Pet objects by ownerId
        List <Pet> customerPets = petService.findPetsByOwner(ownerId);
        //Convert list of Pet objects to list of PetDTO objects
        List<PetDTO> customerPetsDTO = customerPets.stream()
                .map(PetController::convertPetToPetDTO)
                .collect(Collectors.toList());

        return customerPetsDTO;
    }

    //helper methods

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        long ownerId = petDTO.getOwnerId();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(customerService.findCustomer(ownerId));
        return pet;
    }

    private static PetDTO convertPetToPetDTO(Pet pet){
        Long ownerId = pet.getCustomer().getId();
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(ownerId);
        return petDTO;
    }


}
