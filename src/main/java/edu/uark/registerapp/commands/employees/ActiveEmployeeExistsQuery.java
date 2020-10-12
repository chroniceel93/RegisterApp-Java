package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.repositories.EmployeeRepository;


// @Service - annotation that denotes this class as a service. What do?
@Service
public class ActiveEmployeeExistsQuery implements ResultCommandInterface<Employee> {
    
    // @Override completely replaces a function from a template
    @Override
    public Employee execute() {
        boolean doWeExist = 
            this.employeeRepository.existsByIsActive(true);

        if (!doWeExist){
            throw new NotFoundException("Active Employees");
        }
        return null;
    }

    @Autowired
    EmployeeRepository employeeRepository;

}