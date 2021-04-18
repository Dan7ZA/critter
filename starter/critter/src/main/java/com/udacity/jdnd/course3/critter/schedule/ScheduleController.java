package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.entity.User;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.UserController;
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

        List<ScheduleDTO> scheduleDTO = scheduleList.stream()
                .map(ScheduleController::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
        return scheduleDTO;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        throw new UnsupportedOperationException();
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
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
