package com.app.wishlist.service;

import com.app.wishlist.model.Wishlist;
import com.app.wishlist.repository.WishlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistService.class);

    @Autowired
    private WishlistRepository wishlistRepository;

    public List<Wishlist> findingAllWishlist() {
        return wishlistRepository.findAll();
    }

    public Wishlist findingWishlistById(String id) {
        return wishlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The wishlist does not exist."));
    }

    public Wishlist creatingWishlist(Wishlist wishlist) {
        if(checkingEqualWishlist(wishlist)) {
            logger.info("The wishlist is equals.");
            return wishlistRepository.findByName(wishlist.getName());
        }

        try {
            wishlist = wishlistRepository.save(wishlist);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }

        return wishlist;
    }

    public Wishlist updatingWishlist(Wishlist wishlist) {
        verifyIfIdIsValid(wishlist.getId());

        try {
            wishlist = wishlistRepository.save(wishlist);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }

        return wishlist;
    }

    public void deletingWishlist(String id) {
        verifyIfIdIsValid(id);
        wishlistRepository.deleteById(id);
    }

    public boolean checkingEqualWishlist(Wishlist wishlist) {
        final Wishlist checkingWishlistForName = wishlistRepository.findByName(wishlist.getName());

        if(checkingWishlistForName == null) {
            return false;
        }

        return wishlist.getName().equals(checkingWishlistForName.getName());
    }

    public void verifyIfIdIsValid(String id) {
        findingWishlistById(id);
    }

    public Wishlist findingByProductsAndClient(String idProduct, String idClient) {
        return wishlistRepository.findByProductsAndClient(idProduct, idClient)
                .orElseThrow(() -> new IllegalArgumentException("The wishlist does not exist this product and client."));
    }
}