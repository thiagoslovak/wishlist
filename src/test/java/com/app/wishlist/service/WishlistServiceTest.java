package com.app.wishlist.service;

import com.app.wishlist.model.Client;
import com.app.wishlist.model.Product;
import com.app.wishlist.model.Wishlist;
import com.app.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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
public class WishlistServiceTest {

    @Mock
    private Wishlist wishlistMock;
    @Mock
    private Client clientMock;
    @Mock
    private Product productMock;
    @Mock
    private WishlistRepository productRepository;

    @InjectMocks
    private WishlistService wishlistServiceMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindingAllWishlist(){
        when(productRepository.findAll()).thenReturn(Collections.singletonList(wishlistMock));

        final List<Wishlist> wishlist = wishlistServiceMock.findingAllWishlist();

        assertNotNull(wishlist);
        assertFalse(wishlist.isEmpty());

        verify(productRepository).findAll();
    }

    @Test
    void whenFindingWishlistById() {
        when(wishlistMock.getId()).thenReturn("1");
        when(wishlistMock.getName()).thenReturn("Wishlist");
        when(wishlistMock.getClient()).thenReturn(clientMock);
        when(wishlistMock.getProducts()).thenReturn(Collections.singletonList(productMock));
        when(productRepository.findById("1")).thenReturn(Optional.of(wishlistMock));

        final Wishlist wishlist = wishlistServiceMock.findingWishlistById("1");

        assertNotNull(wishlist);
        assertEquals(wishlistMock.getId(), wishlist.getId());
        assertEquals(wishlistMock.getName(), wishlist.getName());
        assertEquals(wishlistMock.getClient(), wishlist.getClient());
        assertEquals(wishlistMock.getProducts(), wishlist.getProducts());

        verify(wishlistMock, times(2)).getId();
        verify(wishlistMock, times(2)).getName();
        verify(wishlistMock, times(2)).getClient();
        verify(wishlistMock, times(2)).getProducts();
        verify(productRepository).findById("1");
    }

    @Test
    void whenFindingWishlistByIdReturnException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wishlistServiceMock.findingWishlistById(null);
        });

        assertNotNull(exception);
        assertEquals("The wishlist does not exist.", exception.getMessage());

        verify(productRepository).findById(any());
    }

    @Test
    void whenCreatingWishlist() {
        when(productRepository.save(wishlistMock)).thenReturn(wishlistMock);

        final Wishlist wishlist = wishlistServiceMock.creatingWishlist(wishlistMock);

        assertNotNull(wishlist);

        verify(productRepository).save(wishlistMock);
    }

    @Test
    void whenCreatingWishlistAndWishlistIsEquals() {
        when(wishlistMock.getId()).thenReturn("1");
        when(wishlistMock.getName()).thenReturn("Wishlist");
        when(wishlistMock.getClient()).thenReturn(clientMock);
        when(wishlistMock.getProducts()).thenReturn(Collections.singletonList(productMock));

        when(productRepository.findByName("Wishlist")).thenReturn(wishlistMock);

        final Wishlist wishlist = wishlistServiceMock.creatingWishlist(wishlistMock);

        assertNotNull(wishlist);
        assertEquals(wishlistMock.getId(), wishlist.getId());
        assertEquals(wishlistMock.getName(), wishlist.getName());
        assertEquals(wishlistMock.getClient(), wishlist.getClient());
        assertEquals(wishlistMock.getProducts(), wishlist.getProducts());

        verify(wishlistMock, times(2)).getId();
        verify(wishlistMock, times(6)).getName();
        verify(wishlistMock, times(2)).getClient();
        verify(wishlistMock, times(2)).getProducts();

        verify(productRepository, times(2)).findByName("Wishlist");
    }

    @Test
    void whenCreatingWishlistReturnException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            wishlistServiceMock.creatingWishlist(null);
        });

        assertNotNull(exception);
    }

    @Test
    void whenUpdatingWishlist() {
        when(wishlistMock.getId()).thenReturn("1");

        when(productRepository.findById("1")).thenReturn(Optional.of(wishlistMock));
        when(productRepository.save(wishlistMock)).thenReturn(wishlistMock);

        final Wishlist wishlist = wishlistServiceMock.updatingWishlist(wishlistMock);

        assertNotNull(wishlist);
        assertEquals(wishlistMock.getId(), wishlist.getId());

        verify(wishlistMock, times(3)).getId();

        verify(productRepository).findById("1");
        verify(productRepository).save(wishlistMock);
    }

    @Test
    void whenUpdatingWishlistReturnException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            wishlistServiceMock.updatingWishlist(null);
        });

        assertNotNull(exception);
    }

    @Test
    void whenDeletingWishlist() {
        when(productRepository.findById("1")).thenReturn(Optional.of(wishlistMock));

        wishlistServiceMock.deletingWishlist("1");

        verify(productRepository).findById("1");
        verify(productRepository).deleteById("1");
    }

    @Test
    void whenCheckingEqualWishlistReturnTrue() {
        when(wishlistMock.getName()).thenReturn("Wishlist");

        when(productRepository.findByName("Wishlist")).thenReturn(wishlistMock);

        boolean response = wishlistServiceMock.checkingEqualWishlist(wishlistMock);

        assertTrue(response);

        verify(wishlistMock, times(3)).getName();

        verify(productRepository).findByName("Wishlist");
    }

    @Test
    void whenCheckingEqualWishlistReturnFalse() {
        boolean response = wishlistServiceMock.checkingEqualWishlist(wishlistMock);

        assertFalse(response);
    }

    @Test
    void whenFindingByProductsAndClient() {
        when(productRepository.findByProductsAndClient("1", "1")).thenReturn(Optional.of(wishlistMock));

        final Wishlist wishlist = wishlistServiceMock.findingByProductsAndClient("1", "1");

        assertNotNull(wishlist);

        verify(productRepository).findByProductsAndClient("1", "1");
    }

    @Test
    void whenFindingByProductsAndClientReturnException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            wishlistServiceMock.findingByProductsAndClient(null, null);
        });

        assertNotNull(exception);
        assertEquals("The wishlist does not exist this product and client.", exception.getMessage());
    }
}