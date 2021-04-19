package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.*;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.UserController;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        // Create a new schedule object & convert the dto to customer in order to save it.
        Schedule newSchedule = this.scheduleService.saveSchedule(this.convertScheduleDTOToSchedule(scheduleDTO));
        // Update customer with newly created ID
        scheduleDTO.setId(newSchedule.getId());
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        //Retrieve list of all customers & covert to DTO
        List<Schedule> scheduleList = scheduleService.getAllSchedules();

        List<ScheduleDTO> scheduleDTOList = scheduleList.stream()
                .map(ScheduleController::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> petSchedule = scheduleService.getScheduleForPet(petId);

        List<ScheduleDTO> scheduleDTOList = petSchedule.stream()
                .map(ScheduleController::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
        return scheduleDTOList;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> employeeSchedule = scheduleService.getScheduleForEmployee(employeeId);

        List<ScheduleDTO> scheduleDTOList = employeeSchedule.stream()
                .map(ScheduleController::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
        return scheduleDTOList;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        //Get list of Pets for Customer
        List<Pet> customerPets = petService.findPetsByOwner(customerId);
        //Initialise array list for Schedule objects
        List<Schedule> customerSchedule = new ArrayList<>();
        //Get Schedules for each Pet in list
        customerPets.forEach((pet) ->
                customerSchedule.addAll(scheduleService.getScheduleForPet(pet.getId())));

        List<ScheduleDTO> scheduleDTOList = customerSchedule.stream()
                .map(ScheduleController::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
        return scheduleDTOList;
    }

    //Helper methods

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();

        BeanUtils.copyProperties(scheduleDTO, schedule);

        //initialise temporary list objects
        List<Employee> employees = Lists.newArrayList();
        List<Pet> pets = Lists.newArrayList();

        //populate temporary list objects from employeeId and petId
        employeeIds.forEach((employeeId) ->
                  employees.add(employeeService.findEmployee(employeeId)));
        petIds.forEach((petId) ->
                pets.add(petService.findPet(petId)));

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return schedule;
    }

    private static ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        List<Long> petIds = new ArrayList<>();
        List<Long> employeeIds = new ArrayList<>();
        if (schedule.getPets() != null) {
            schedule.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
            scheduleDTO.setPetIds(petIds);
        }
        if (schedule.getEmployees() != null) {
            schedule.getEmployees().forEach(employee -> {
                employeeIds.add(employee.getId());
            });
            scheduleDTO.setEmployeeIds(employeeIds);
        }
        return scheduleDTO;
    }
}
