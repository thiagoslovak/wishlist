package com.app.wishlist.repository;

import com.app.wishlist.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String>  {

    Client findByCpf(String cpf);
}