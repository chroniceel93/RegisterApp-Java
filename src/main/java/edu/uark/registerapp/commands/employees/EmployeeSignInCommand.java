package edu.uark.registerapp.commands.employees;

/**
 * Optional - container for an object that may or may not be null
 * 
 * Don't tell schrodinger
 */
import java.util.Optional;
import java.util.Arrays;
import java.util.UUID;

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

        // good, user exists, and password is correct.
        // Now, check to see if user is already signed in.
        final Optional<ActiveUserEntity> activeUserEntity = 
            this.activeUserRepository.findByEmployeeId(UUID.fromString(employeeId));
        if (activeUserEntity.isPresent()) {
            // The user is already logged in. Update the Session key
            activeUserEntity.get().setSessionKey(this.sessionKey);
            activeUserRepository.save(activeUserEntity.get());
        } else {
            //user is not logged in, make new Active User, and save.
            activeUserEntity.get().setSessionKey(this.sessionKey);
            activeUserEntity.get().setEmployeeId(UUID.fromString(employeeId));
            activeUserEntity.get().setName((employeeEntity.get().getFirstName() 
                                          + employeeEntity.get().getLastName()));
            activeUserEntity.get().setClassification(employeeEntity.get().getClassification());
            activeUserRepository.save(activeUserEntity.get());
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

    private EmployeeSignIn employeeSignIn;


    public EmployeeSignInCommand setSessionKey(final String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }


    public EmployeeSignInCommand setEmployeeSignIn(final EmployeeSignIn employeeSignIn) {
        this.employeeSignIn = employeeSignIn;
        return this;
    }
}


/* You know, I have a class that uses ProctorU for tests.

It requires Adobe Flash player to be installed. Problem is, is that
it's reaching EOL. Adobe even added a kill-switch so people can't
use it after December 31st. 

This'll be frustrating for sure.

*/