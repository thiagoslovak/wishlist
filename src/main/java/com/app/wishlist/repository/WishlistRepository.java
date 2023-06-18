package com.app.wishlist.repository;

import com.app.wishlist.model.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String> {

    Wishlist findByName(String name);
    Optional<Wishlist> findByProductsAndClient(String idProduct, String idClient);
}