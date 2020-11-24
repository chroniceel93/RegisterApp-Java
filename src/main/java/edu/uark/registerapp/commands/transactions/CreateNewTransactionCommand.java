package edu.uark.registerapp.commands.transactions;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.models.entities.TransactionEntity;
import edu.uark.registerapp.models.entities.TransactionEntryEntity;
import edu.uark.registerapp.models.repositories.TransactionEntryRepository;
import edu.uark.registerapp.models.repositories.TransactionRepository;

@Service
public class CreateNewTransactionCommand implements VoidCommandInterface {
	@Override
	public void execute() {
		long transactionTotal = 0L;
		final List<TransactionEntryEntity> transactionEntryEntities = new LinkedList<>();
        

        this.createNewTransaction(transactionEntryEntities, transactionTotal);
    
    }

	// Helper methods
	@Transactional
	private void createNewTransaction(
		final List<TransactionEntryEntity> transactionEntryEntities,
		final long transactionTotal
	) {

		final TransactionEntity transactionEntity =
			this.transactionRepository.save(
				(new TransactionEntity(this.employeeId, transactionTotal, 1)));

		for (TransactionEntryEntity transactionEntryEntity : transactionEntryEntities) {
			transactionEntryEntity.setTransactionId(transactionEntity.getId());

			this.transactionEntryRepository.save(transactionEntryEntity);
		}
	}

	// Properties
	private UUID employeeId;
	public UUID getEmployeeId() {
		return this.employeeId;
	}
	public CreateNewTransactionCommand setEmployeeId(final UUID employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionEntryRepository transactionEntryRepository;
}
