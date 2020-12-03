package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.repositories.TransactionRepository;


@Service
public class TransactionCalculationCommand implements VoidCommandInterface {
    @Override
    public void execute() {
        final Optional<TransactionEntity> transactionEntity =
            this.transactionRepository.findById(this.transactionId);
            
        if (transactionEntity.isPresent()) {
            // Do things?
    
        } else {
            // there is no transaction
        }
    }

private UUID transactionId;
public UUID getTransactionId() {
    return this.transactionId;
}

private UUID cashierId;
public void setCashierId(UUID cashId) {
    this.cashierId = cashId;
}


@Autowired
private TransactionRepository transactionRepository;
}
