package com.pragma.powerup.usermicroservice.adapters.driving.http.Adapter;

import com.pragma.powerup.usermicroservice.adapters.driving.http.exceptions.RestaurantNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpAdapterImplTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private HttpAdapterImpl httpAdapterImpl;

    @Value("${app.urls.urlToRestaurantMicroService}")
    String urlToRestaurant;

    @Test
    void checkRestaurant() {
        String token = "TestToken";
        String url = urlToRestaurant + "restaurant/ownerRestaurants/{idOwner}";
        Integer idOwner = 1;
        List data = new ArrayList();
        data.add("testBody");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = new ResponseEntity<>(data, HttpStatus.OK);

        when(restTemplate.exchange(url, HttpMethod.GET, entity, List.class, idOwner)).thenReturn(response);

        httpAdapterImpl.checkRestaurant(idOwner, token);

        verify(restTemplate, times(1)).exchange(url, HttpMethod.GET, entity, List.class, idOwner);
    }
    @Test
    void checkRestaurantConflict() {
        String token = "TestToken";
        String url = urlToRestaurant + "restaurant/ownerRestaurants/{idOwner}";
        Integer idOwner = 1;
        List data = new ArrayList();
        data.add("testBody");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = new ResponseEntity<>(HttpStatus.CONFLICT);

        when(restTemplate.exchange(url, HttpMethod.GET, entity, List.class, idOwner)).thenReturn(response);

        assertThrows(RestaurantNotFoundException.class,()->httpAdapterImpl.checkRestaurant(idOwner,token));
    }

    @Test
    void checkRestaurantBodyEmpty() {
        String token = "TestToken";
        String url = urlToRestaurant + "restaurant/ownerRestaurants/{idOwner}";
        Integer idOwner = 1;
        List data = new ArrayList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = new ResponseEntity<>(data,HttpStatus.OK);

        when(restTemplate.exchange(url, HttpMethod.GET, entity, List.class, idOwner)).thenReturn(response);

        assertThrows(RestaurantNotFoundException.class,()->httpAdapterImpl.checkRestaurant(idOwner,token));
    }
}