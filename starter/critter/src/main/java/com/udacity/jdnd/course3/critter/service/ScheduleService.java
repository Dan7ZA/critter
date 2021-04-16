package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public void eatSchedule(Schedule schedule){
        scheduleRepository.save(schedule);
    }

    public Schedule expelSchedule(Long id){
        return scheduleRepository.findById(id).orElse(null);
    }

}
