package com.app.wishlist.service;

import com.app.wishlist.model.Client;
import com.app.wishlist.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findingAllClient() {
        return clientRepository.findAll();
    }

    public Client findingClientById(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The client does not exist."));
    }

    public Client creatingClient(Client client) {
        if(checkingEqualClient(client)) {
            logger.info("The clint is equals.");
            return clientRepository.findByCpf(client.getCpf());
        }

        try {
            client = clientRepository.save(client);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return client;
    }

    public Client updatingClient(Client client) {
        try {
            client = clientRepository.save(checkingIdIsNotNull(client));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return client;
    }

    public void deletingClient(String id) {
        verifyIfIdIsValid(id);
        clientRepository.deleteById(id);
    }

    public boolean checkingEqualClient(Client client) {
        final Client clientForChecking = clientRepository.findByCpf(client.getCpf());

        if(clientForChecking == null) {
            return false;
        }

        return client.getCpf().equals(clientForChecking.getCpf());
    }

    public Client checkingIdIsNotNull(Client client) {
        if(client.getId() != null) {
            return client;
        }

        return clientRepository.findByCpf(client.getCpf());
    }

    public void verifyIfIdIsValid(String id) {
        findingClientById(id);
    }
}