// Not sure if we're ever actually going to use this one.
// I can finish it up pretty quickly if we do need it.


package edu.uark.registerapp.commands.transactions;

import java.util.UUID;


// import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.VoidCommandInterface;
// import edu.uark.registerapp.models.entities.ProductEntity;
// import edu.uark.registerapp.models.entities.TransactionEntity;
// import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;
// import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
// import edu.uark.registerapp.models.repositories.TransactionRepository;


@Service
public class TransactionSearchByID implements VoidCommandInterface{
    @Override
    public void execute() {
        //do_nothing
    }


    // Properties
    private UUID employeeId;
    public UUID getEmployeeId() {
        return this.employeeId;
    }

    @Autowired
    ProductRepository productRepository;
}