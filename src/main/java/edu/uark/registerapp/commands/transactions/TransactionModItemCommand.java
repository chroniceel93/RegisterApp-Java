package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import edu.uark.registerapp.models.repositories.TransactionRepository;

@Service
public class TransactionModItemCommand implements VoidCommandInterface {
    @Override
    public void execute() {

    }

    private UUID transactionId;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired 
    private TransactionEntryRepository transactionEntryRepository;
}