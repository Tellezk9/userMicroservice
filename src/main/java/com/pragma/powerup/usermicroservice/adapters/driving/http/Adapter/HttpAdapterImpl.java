package com.pragma.powerup.usermicroservice.adapters.driving.http.Adapter;

import com.pragma.powerup.usermicroservice.adapters.driving.http.exceptions.ErrorExecutionException;
import com.pragma.powerup.usermicroservice.adapters.driving.http.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.domain.geteway.IHttpAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HttpAdapterImpl implements IHttpAdapter {
    private final RestTemplate restTemplate;

    @Value("${app.urls.urlToRestaurantMicroService}")
    String urlToRestaurant;

    @Override
    public void checkRestaurant(Integer idOwner, String token) {
        ResponseEntity<List> response = new ResponseEntity<>(HttpStatusCode.valueOf(400));
        String bearerToken = "Bearer " + token;
        String url = urlToRestaurant + "restaurant/ownerRestaurants/{idOwner}";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", bearerToken);
        HttpEntity entity = new HttpEntity<>(headers);

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class, idOwner);
        } catch (Exception ex) {
            throw new ErrorExecutionException();
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RestaurantNotFoundException();
        }
        if (response.getBody() == null || response.getBody().isEmpty()) {
            throw new RestaurantNotFoundException();
        }
    }
}
