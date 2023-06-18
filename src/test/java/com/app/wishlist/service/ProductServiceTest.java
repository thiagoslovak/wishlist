package com.app.wishlist.service;

import com.app.wishlist.model.Product;
import com.app.wishlist.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    
    @Mock
    private Product productMock;
    @Mock
    private ProductRepository productRepositoryMock;

    @InjectMocks
    private ProductService productServiceMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindingAllProduct(){
        when(productRepositoryMock.findAll()).thenReturn(Collections.singletonList(productMock));

        final List<Product> product = productServiceMock.findingAllProduct();

        assertNotNull(product);
        assertFalse(product.isEmpty());

        verify(productRepositoryMock).findAll();
    }

    @Test
    void whenFindingProductById() {
        when(productMock.getId()).thenReturn("1");
        when(productMock.getName()).thenReturn("Smart TV 43 UHD 4K LG");
        when(productMock.getColor()).thenReturn("Preto");
        when(productMock.getValue()).thenReturn(BigDecimal.valueOf(1499));
        when(productMock.getDetails()).thenReturn("Prepare-se para experienciar um novo nível de imersão televisiva com a TV LG UHD AU7700.");

        when(productRepositoryMock.findById("1")).thenReturn(Optional.of(productMock));

        final Product product = productServiceMock.findingProductById("1");

        assertNotNull(product);
        assertEquals(productMock.getId(), product.getId());
        assertEquals(productMock.getName(), product.getName());
        assertEquals(productMock.getColor(), product.getColor());
        assertEquals(productMock.getValue(), product.getValue());
        assertEquals(productMock.getDetails(), product.getDetails());

        verify(productMock, times(2)).getId();
        verify(productMock, times(2)).getName();
        verify(productMock, times(2)).getColor();
        verify(productMock, times(2)).getValue();
        verify(productMock, times(2)).getDetails();
        verify(productRepositoryMock).findById("1");
    }

    @Test
    void whenFindingProductByIdReturnException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productServiceMock.findingProductById(null);
        });

        assertNotNull(exception);
        assertEquals("The product does not exist.", exception.getMessage());

        verify(productRepositoryMock).findById(any());
    }

    @Test
    void whenCreatingProduct() {
        when(productRepositoryMock.save(productMock)).thenReturn(productMock);

        final Product product = productServiceMock.creatingProduct(productMock);

        assertNotNull(product);

        verify(productRepositoryMock).save(productMock);
    }

    @Test
    void whenCreatingProductAndProductEquals() {
        when(productMock.getId()).thenReturn("1");
        when(productMock.getName()).thenReturn("Smart TV 43 UHD 4K LG");
        when(productMock.getColor()).thenReturn("Preto");
        when(productMock.getValue()).thenReturn(BigDecimal.valueOf(1499));
        when(productMock.getDetails()).thenReturn("Prepare-se para experienciar um novo nível de imersão televisiva com a TV LG UHD AU7700.");

        when(productRepositoryMock.findByName("Smart TV 43 UHD 4K LG")).thenReturn(productMock);

        final Product product = productServiceMock.creatingProduct(productMock);

        assertNotNull(product);
        assertEquals(productMock.getId(), product.getId());
        assertEquals(productMock.getName(), product.getName());
        assertEquals(productMock.getColor(), product.getColor());
        assertEquals(productMock.getValue(), product.getValue());
        assertEquals(productMock.getDetails(), product.getDetails());

        verify(productMock, times(2)).getId();
        verify(productMock, times(6)).getName();
        verify(productMock, times(2)).getColor();
        verify(productMock, times(2)).getValue();
        verify(productMock, times(2)).getDetails();

        verify(productRepositoryMock, times(2)).findByName("Smart TV 43 UHD 4K LG");
    }

    @Test
    void whenCreatingProductReturnException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            productServiceMock.creatingProduct(null);
        });

        assertNotNull(exception);
    }

    @Test
    void whenUpdatingProduct() {
        when(productMock.getId()).thenReturn("1");

        when(productRepositoryMock.findById("1")).thenReturn(Optional.of(productMock));
        when(productRepositoryMock.save(productMock)).thenReturn(productMock);

        final Product product = productServiceMock.updatingProduct(productMock);

        assertNotNull(product);
        assertEquals(productMock.getId(), product.getId());

        verify(productMock, times(3)).getId();

        verify(productRepositoryMock).findById("1");
        verify(productRepositoryMock).save(productMock);
    }

    @Test
    void whenUpdatingProductReturnException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            productServiceMock.updatingProduct(null);
        });

        assertNotNull(exception);
    }

    @Test
    void whenDeletingProduct() {
        when(productRepositoryMock.findById("1")).thenReturn(Optional.of(productMock));

        productServiceMock.deletingProduct("1");

        verify(productRepositoryMock).findById("1");
        verify(productRepositoryMock).deleteById("1");
    }

    @Test
    void whenCheckingEqualProductReturnTrue() {
        when(productMock.getName()).thenReturn("Smart TV 43 UHD 4K LG");

        when(productRepositoryMock.findByName("Smart TV 43 UHD 4K LG")).thenReturn(productMock);

        boolean response = productServiceMock.checkingEqualProduct(productMock);

        assertTrue(response);

        verify(productMock, times(3)).getName();

        verify(productRepositoryMock).findByName("Smart TV 43 UHD 4K LG");
    }

    @Test
    void whenCheckingEqualProductReturnFalse() {
        boolean response = productServiceMock.checkingEqualProduct(productMock);

        assertFalse(response);
    }
}