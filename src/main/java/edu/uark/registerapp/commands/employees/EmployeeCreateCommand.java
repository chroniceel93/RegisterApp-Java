package edu.uark.registerapp.commands.employees;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class EmployeeCreateCommand implements ResultCommandInterface<Employee> {
    @Override
    public Employee execute() {
        this.validateProperties();

        final EmployeeEntity createEmployeeEntity = 
            this.employeeRepository.save(
                new EmployeeEntity(this.getApiEmployee()));
        // Synchronize information generated by the database upon INSERT.
        this.getApiEmployee().setId(createEmployeeEntity.getId());
        this.getApiEmployee().setManagerId(createEmployeeEntity.getManagerId());
        this.getApiEmployee().setEmployeeId(createEmployeeEntity.getEmployeeId());

        
        return this.getApiEmployee();
    }

    private void validateProperties() {
        if(StringUtils.isBlank(this.apiEmployee.getFirstName())) {
            throw new UnprocessableEntityException("firstName");
        }
        if(StringUtils.isBlank(this.apiEmployee.getLastName())) {
            throw new UnprocessableEntityException("lastName");
        }
        if(StringUtils.isBlank(this.apiEmployee.getPassword())) {
            throw new UnprocessableEntityException("password");
        }
        if(this.getIsInitialEmployee()) {
            this.getApiEmployee().setClassification(
                EmployeeClassification.GENERAL_MANAGER.getClassification());
        }
    }


    // Properties
    private Employee apiEmployee;

    public Employee getApiEmployee() {
        return this.apiEmployee;
    }

    public EmployeeCreateCommand setApiEmployee(final Employee apiEmployee) {
        this.apiEmployee = apiEmployee;
        return this;
    }

    private boolean isInitialEmployee;

    public boolean getIsInitialEmployee() {
        return this.isInitialEmployee;
    }

    public EmployeeCreateCommand setInitialEmployee(final boolean isInitialEmployee) {
        this.isInitialEmployee = isInitialEmployee;
        return this;
    }

    @Autowired
    private EmployeeRepository employeeRepository;
}