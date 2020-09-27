package edu.uark.registerapp.commands.employees;

/**
 * Optional - container for an object that may or may not be null
 * 
 * Don't tell schrodinger
 */
import java.util.Optional;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.commands.exceptions.ConflictException;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.api.EmployeeSignIn;

@Service
public class EmployeeSignInCommand implements VoidCommandInterface {
    @Transactional
    @Override
    public void execute() {
        final String employeeId = 
            this.employeeSignIn.getEmployeeId();
        // first, check for null or missing input
        if (employeeId == "" || employeeId == null ) {
            throw new UnprocessableEntityException("Employee ID"); 
        }
        // check to see if employee id is a number
        try {
            double throwaway = Double.parseDouble(employeeId);
        } catch (NumberFormatException nfe) {
            throw new UnprocessableEntityException("Input must be a numerical value.");
        }
        // check to see if employee exists
        final Optional<EmployeeEntity> employeeEntity =
            this.employeeRepository.findByEmployeeId(Integer.parseInt(employeeId));
        // exception if user does not exist
        if (!employeeEntity.isPresent()) {
            throw new NotFoundException("Employee");
        }

        // verify password
        if (!Arrays.equals(this.employeeSignIn.getPassword().getBytes(), employeeEntity.get().getPassword())) {
            throw new ConflictException("Password");
        }


    }
    
    // Properties
    private String sessionKey;
    public String getSessionKey() {
        return this.sessionKey;
    }

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private ActiveUserRepository activeUserRepository;

    @Autowired
    private EmployeeSignIn employeeSignIn;
}


/* You know, I have a class that uses ProctorU for tests.

It requires Adobe Flash player to be installed. Problem is, is that
it's reaching EOL. Adobe even added a kill-switch so people can't
use it after December 31st. 

This'll be frustrating for sure.

*/