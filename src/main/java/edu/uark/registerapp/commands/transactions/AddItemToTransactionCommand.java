package edu.uark.registerapp.commands.transactions;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.models.entities.ProductEntity; //JIC
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import edu.uark.registerapp.models.repositories.TransactionRepository;
/*
@Service
public class AddItemToTransactionCommand implements ResultCommandInterface<TransactionEntry> {
    @Override
    public Transaction execute() {
    }

    private UUID transactionId;
    public UUID getTransactionId() {
        return this.transactionId;
    }

    private UUID productId;
    public UUID getProductId() {
        return this.productId;
    }

    @Transactional
    private TransactionEntryEntity createProductEntity() {
        final Optional<TransactionEntryEntity> queried
    }

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired 
    private TransactionEntryRepository transactionEntryRepository;

    @Autowired
    private ProductRepository productRepository;
}

*/