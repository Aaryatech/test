package com.example.demo.service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
 
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.FirebaseResponse;
 
@Service
public class AndroidPushNotificationsService {
 
	private static final String FIREBASE_SERVER_KEY = "AAAACpsBOEg:APA91bFVOnx5bb4ba0NfWsTuEF2JxSJc0RESZ5LSZHRVh4aXH3JHgf4scdQZ6igqW2RcaFdvNyv4EDxB8vUyOG5PQmo7eD1VENfGtQW0RZ5ZWsu8XBP0aRAerHPLsk6eEAeiHf-V2kSR";

    @Async
    public CompletableFuture<FirebaseResponse> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        FirebaseResponse firebaseResponse = restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", entity, FirebaseResponse.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }


}