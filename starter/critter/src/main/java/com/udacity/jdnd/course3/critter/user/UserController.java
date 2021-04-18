package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.User;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        // Create a new customer object & convert the dto to customer in order to save it.
        Customer newCustomer = this.customerService.saveCustomer(this.convertCustomerDTOtoCustomer(customerDTO));
        // Update customer with newly created ID
        customerDTO.setId(newCustomer.getId());
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        //Retrieve list of all customers & covert to DTO
        List<Customer> customerList = customerService.getAllCustomers();

        List<CustomerDTO> customerDTO = customerList.stream()
                .map(UserController::convertCustomerToCustomerDTO)
                .collect(Collectors.toList());
        return customerDTO;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        //Retrieve customer by Pet ID
        //CustomerDTO customerDTO = convertCustomerToCustomerDTO((Customer) this.userService.getOwnerByPet(petId));
        //return customerDTO;
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        // Create a new employee object & convert the dto to employee in order to save it.
        Employee newEmployee = this.employeeService.saveEmployee(this.convertEmployeeDTOToEmployee(employeeDTO));
        // Update employee with newly created ID
        employeeDTO.setId(newEmployee.getId());
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        //Initialise employee object from DB
        Employee employee = this.employeeService.findEmployee(employeeId);
        //Convert to employee object to employeeDTO & return
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        this.employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    private Customer convertCustomerDTOtoCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private static CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Long> petIds = new ArrayList<>();
        if (customer.getPets() != null) {
            customer.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

}
