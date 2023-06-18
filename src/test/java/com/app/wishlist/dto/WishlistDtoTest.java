package com.app.wishlist.dto;

import com.app.wishlist.model.Client;
import com.app.wishlist.model.Product;
import com.app.wishlist.model.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WishlistDtoTest {

    @Mock
    private Wishlist wishlistMock;
    @Mock
    private Client clientMock;
    @Mock
    private Product productMock;
    @Mock
    private WishlistDto wishlistDtoMock;

    @InjectMocks
    private WishlistDto.RepresentationBuilder representationBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenToRepresentation() {
        when(wishlistMock.getId()).thenReturn("1");
        when(wishlistMock.getName()).thenReturn("Wishlist");
        when(wishlistMock.getClient()).thenReturn(clientMock);
        when(wishlistMock.getProducts()).thenReturn(Collections.singletonList(productMock));

        final WishlistDto dto = representationBuilder.toRepresentation(wishlistMock);

        assertNotNull(dto);
        assertEquals(wishlistMock.getId(), dto.getId());
        assertEquals(wishlistMock.getName(), dto.getName());
        assertEquals(wishlistMock.getClient(), dto.getClient());
        assertEquals(wishlistMock.getProducts(), dto.getProducts());

        verify(wishlistMock, times(2)).getId();
        verify(wishlistMock, times(2)).getName();
        verify(wishlistMock, times(2)).getClient();
        verify(wishlistMock, times(2)).getProducts();
    }

    @Test
    void whenFromRepresentation() {
        when(wishlistDtoMock.getId()).thenReturn("1");
        when(wishlistDtoMock.getName()).thenReturn("Wishlist");
        when(wishlistDtoMock.getClient()).thenReturn(clientMock);
        when(wishlistDtoMock.getProducts()).thenReturn(Collections.singletonList(productMock));

        final Wishlist wishlist = representationBuilder.fromRepresentation(wishlistDtoMock);

        assertNotNull(wishlist);
        assertEquals(wishlistDtoMock.getId(), wishlist.getId());
        assertEquals(wishlistDtoMock.getName(), wishlist.getName());
        assertEquals(wishlistDtoMock.getClient(), wishlist.getClient());
        assertEquals(wishlistDtoMock.getProducts(), wishlist.getProducts());

        verify(wishlistDtoMock, times(2)).getId();
        verify(wishlistDtoMock, times(2)).getName();
        verify(wishlistDtoMock, times(2)).getClient();
        verify(wishlistDtoMock, times(2)).getProducts();
    }
}