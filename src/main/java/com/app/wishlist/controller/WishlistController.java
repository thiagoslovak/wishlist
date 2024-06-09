package com.app.wishlist.controller;

import com.app.wishlist.dto.WishlistDto;
import com.app.wishlist.model.Wishlist;
import com.app.wishlist.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
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

import static java.lang.String.format;

@Slf4j
@RestController
@RequestMapping("/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;
    private final WishlistDto.RepresentationBuilder builder = new WishlistDto.RepresentationBuilder();

    @GetMapping
    @Operation(summary = "Realiaza a busca das wishlists.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wishlists retornado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca das wishlists.")
    })
    public List<WishlistDto> findingAll() {
        log.info("Buscando dados de todos as wishlists.");
        List<Wishlist> wishlists = wishlistService.findingAllWishlist();
        return wishlists.stream().map(builder::toRepresentation).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Realiaza a busca da wishlist por id.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wishlist retornado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca da wishlist.")
    })
    public ResponseEntity<WishlistDto> findingById(@PathVariable final String id) {
        log.info(format("Buscando dados da wishlist com id: %s.", id));
        final Wishlist wishlists = wishlistService.findingWishlistById(id);
        return ResponseEntity.status(HttpStatus.OK).body(builder.toRepresentation(wishlists));
    }

    @GetMapping("/products")
    @Operation(summary = "Realiaza a busca de cliente e produto por id.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca.")
    })
    public ResponseEntity<WishlistDto> findByProductsAndClient(@RequestParam final String idProduct, @RequestParam String idClient) {
        log.info(format("Buscando dados do produto com id: %s e cliente com id: %s.", idProduct, idClient));
        return ResponseEntity.status(HttpStatus.OK).body(builder
                .toRepresentation(wishlistService.findingByProductsAndClient(idProduct, idClient)));
    }

    @PostMapping
    @Operation(summary = "Realiaza a criação da wishlist.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wishlist criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a criação da wishlist.")
    })
    public ResponseEntity<WishlistDto> creating(@RequestBody @Valid final WishlistDto dto) {
        log.info("Criação da wishlist iniciado!");
        final Wishlist wishlist = wishlistService.creatingWishlist(builder.fromRepresentation(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(builder.toRepresentation(wishlist));
    }

    @PutMapping
    @Operation(summary = "Realiaza a atualização da wishlist.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wishlist atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a atualização da wishlist.")
    })
    public ResponseEntity<WishlistDto> updating(@RequestBody @Valid final WishlistDto dto) {
        log.info("Atualização da wishlist iniciado!");
        final Wishlist wishlist = wishlistService.updatingWishlist(builder.fromRepresentation(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(builder.toRepresentation(wishlist));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Realiaza a remoção da wishlist.", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wishlist removido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a remoção da wishlist.")
    })
    public ResponseEntity<Object> deleting(@PathVariable final String id) {
        wishlistService.deletingWishlist(id);
        log.info(format("Wishlist com id: %s, deletado.", id));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}