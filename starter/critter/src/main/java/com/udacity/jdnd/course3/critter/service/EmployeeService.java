package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.User;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public List<Employee> findEmployeesBySkill(EmployeeSkill employeeSkill){
        return employeeRepository.findBySkills(employeeSkill);
    }

    public List<Employee> findEmployeesByDaysAvailable(DayOfWeek requestedDay){
        return employeeRepository.findByDaysAvailable(requestedDay);
    }

    public List<Employee> getEmployeesForService(DayOfWeek requestedDay, Set<EmployeeSkill> requestedSkills){

        List<Employee> availableEmployeesWithOneOfTheSkills = employeeRepository.findEmployeesByDaysAvailableAndSkillsIn(requestedDay, requestedSkills);
        List<Employee> availableEmployeesWithAllOfTheSkills = new ArrayList<>();

        //Remove duplicates caused by employee having more than one of the request skills
        Set<Employee> set = new HashSet<>(availableEmployeesWithOneOfTheSkills);
        availableEmployeesWithOneOfTheSkills.clear();
        availableEmployeesWithOneOfTheSkills.addAll(set);

        //Find employees with all of the requested skills
        for (Employee e : availableEmployeesWithOneOfTheSkills){
            if(e.getSkills().containsAll(requestedSkills)){
                availableEmployeesWithAllOfTheSkills.add(e);
            }
        }
        return availableEmployeesWithAllOfTheSkills;
    }
}
