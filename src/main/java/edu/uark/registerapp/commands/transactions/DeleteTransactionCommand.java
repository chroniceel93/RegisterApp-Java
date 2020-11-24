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
public class DeleteTransactionCommand implements VoidCommandInterface {
	@Override
	public void execute() {
		final Optional<TransactionEntity> transactionEntity = 
			this.transactionRepository.findById(this.transactionId);

		if (transactionEntity.isPresent()) {
			this.transactionEntryRepository.deleteById(this.transactionId);
		}
			this.transactionRepository.delete(transactionEntity.get());
	}

	// Properties
	private UUID transactionId;
	public UUID getTransactionId() {
		return this.transactionId;
	}

	public DeleteTransactionCommand setTransactionId(final UUID transactionId) {
		this.transactionId = transactionId;
		return this;
	}



	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionEntryRepository transactionEntryRepository;
}
