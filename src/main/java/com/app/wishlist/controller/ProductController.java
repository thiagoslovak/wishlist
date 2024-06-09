package com.app.wishlist.controller;

import com.app.wishlist.dto.ProductDto;
import com.app.wishlist.model.Product;
import com.app.wishlist.service.ProductService;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    private final ProductDto.RepresentationBuilder builder = new ProductDto.RepresentationBuilder();

    @GetMapping
    @Operation(summary = "Realiaza a busca de produtos.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos retornado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos produtos.")
    })
    public List<ProductDto> findingAll() {
        log.info("Buscando dados de todos os produtos.");
        List<Product> products = productService.findingAllProduct();
        return products.stream().map(builder::toRepresentation).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Realiaza a busca de produto por id.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto retornado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca do produto.")
    })
    public ResponseEntity<ProductDto> findingById(@PathVariable final String id) {
        log.info(format("Buscando dados do produto com id: %s.", id));
        final Product product = productService.findingProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(builder.toRepresentation(product));
    }

    @PostMapping
    @Operation(summary = "Realiaza a criação de produto.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a criação do produto.")
    })
    public ResponseEntity<ProductDto> creating(@RequestBody @Valid final ProductDto dto) {
        log.info("Criação do produto iniciado!");
        final Product product = productService.creatingProduct(builder.fromRepresentation(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(builder.toRepresentation(product));
    }

    @PutMapping
    @Operation(summary = "Realiaza a atualização de produto.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a atualização do produto.")
    })
    public ResponseEntity<ProductDto> updating(@RequestBody @Valid final ProductDto dto) {
        log.info("Atualização do produto iniciado!");
        final Product product = productService.updatingProduct(builder.fromRepresentation(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(builder.toRepresentation(product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Realiaza a remoção de produto.", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto removido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a remoção do produto.")
    })
    public ResponseEntity<Object> deleting(@PathVariable final String id) {
        productService.deletingProduct(id);
        log.info(format("Produto com id: %s, deletado.", id));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}