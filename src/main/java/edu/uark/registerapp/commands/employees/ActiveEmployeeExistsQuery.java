package edu.uark.registerapp.commands.employees;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factor.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;


// @Service - annotation that denotes this class as a service. What do?
@Service
public class ActiveEmployeeExistsQuery implements ResultCommandInterface<Employee> {
    
    // @Override completely replaces a function from a template
    @Override
    public Employee execute() {
        boolean internal = false;
        
        boolean doWeExist = 
            this.employeeRepositoy.existsByID(true);

        if (!doWeExist){
            throw new NotFoundException("Active Employees");
        }
    }

}