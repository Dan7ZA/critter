package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.User;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.Set;

@Transactional
@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee findEmployee(Long employeeId){
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId){
        employeeRepository.getOne(employeeId).setDaysAvailable(daysAvailable);
    }
}
