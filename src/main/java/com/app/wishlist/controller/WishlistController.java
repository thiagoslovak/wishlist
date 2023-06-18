package com.app.wishlist.controller;

import com.app.wishlist.dto.WishlistDto;
import com.app.wishlist.model.Wishlist;
import com.app.wishlist.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;
    private final WishlistDto.RepresentationBuilder builder = new WishlistDto.RepresentationBuilder();

    @GetMapping
    public List<WishlistDto> findingAll() {
        List<Wishlist> wishlists = wishlistService.findingAllWishlist();

        return wishlists.stream().map(builder::toRepresentation).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishlistDto> findingById(@PathVariable final String id) {
        final Wishlist wishlists = wishlistService.findingWishlistById(id);

        return ResponseEntity.status(HttpStatus.OK).body(builder.toRepresentation(wishlists));
    }

    @GetMapping("/products")
    public ResponseEntity<WishlistDto> findByProductsAndClient(@RequestParam final String idProduct, @RequestParam String idClient) {
        return ResponseEntity.status(HttpStatus.OK).body(builder
                .toRepresentation(wishlistService.findingByProductsAndClient(idProduct, idClient)));
    }

    @PostMapping
    public ResponseEntity<WishlistDto> creating(@RequestBody @Valid final WishlistDto dto) {
        final Wishlist wishlist = wishlistService.creatingWishlist(builder.fromRepresentation(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(builder.toRepresentation(wishlist));
    }

    @PutMapping
    public ResponseEntity<WishlistDto> updating(@RequestBody @Valid final WishlistDto dto) {
        final Wishlist wishlist = wishlistService.updatingWishlist(builder.fromRepresentation(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(builder.toRepresentation(wishlist));
    }

    @DeleteMapping("/{id}")
    public void deleting(@PathVariable final String id) {
        wishlistService.deletingWishlist(id);
    }
}