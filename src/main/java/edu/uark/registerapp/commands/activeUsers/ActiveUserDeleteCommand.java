package edu.uark.registerapp.commands.activeusers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.entitites.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

@Service
public class ActiveUserDeleteCommand implements VoidCommandInterface {
    // this all should work, I think. Modelled it after ProductDeleteCommand.java
    @Transactional
    @Override
    public void execute() {
        final Optional<ActiveUserEntity> activeUserEntity = 
            this.activeUserRepository.findBySessionKey(this.sessionKey);
        if (!activeUserEntity.isPresent()) {
            throw new NotFoundException("Active User");
        }

        this.activeUserRepository.delete(activeUserEntity.get());
        }       
    // Proprties
    private String sessionKey;

    public String getSessionKey() {
        return this.sessionKey;
    }

    public ActiveUserDeleteCommand setSessionKey(final UUID sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    @Autowired
    private ActiveUserRepository activeUserRepository;
}
