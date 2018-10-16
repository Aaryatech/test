package com.example.demo.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Data;
import com.example.demo.model.ErrorMessage;
import com.example.demo.model.FirebaseResponse;
import com.example.demo.model.Payload;
import com.example.demo.service.AndroidPushNotificationsService;
import com.example.demo.service.HeaderRequestInterceptor;

import java.util.ArrayList;




import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController
public class WelcomeController {


	String androidFcmKey = "AAAACpsBOEg:APA91bFVOnx5bb4ba0NfWsTuEF2JxSJc0RESZ5LSZHRVh4aXH3JHgf4scdQZ6igqW2RcaFdvNyv4EDxB8vUyOG5PQmo7eD1VENfGtQW0RZ5ZWsu8XBP0aRAerHPLsk6eEAeiHf-V2kSR";
	String androidFcmUrl = "https://fcm.googleapis.com/fcm/send";
	String deviceToken = "fIbUvPSEpME:APA91bGItkFyMUvrD1RH-4TM3eQEO2zVTuiabzZDZ6SrIAZpQCAbOCjJSTRRDzM0t-ifdkFUgvr22nkv5UmY0Q8zg3dmYNgeYZSwoNyXlLdyrNVy7H89RxEpQCbPXY1xsdVlry8b10AT";
		
	private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);

    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;
      
    
    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> send() {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss");

		Date date = new Date();
		System.out.println(new Timestamp(date.getTime()));
    	
        JSONObject body = new JSONObject();
        // JsonArray registration_ids = new JsonArray();
        // body.put("registration_ids", registration_ids);
        body.put("to", deviceToken);
        body.put("priority", "high");
        // body.put("dry_run", true);

        JSONObject notification = new JSONObject();
        notification.put("body", "body string here");
        notification.put("title", "title string here");
        // notification.put("icon", "myicon");

        JSONObject data = new JSONObject();
        data.put("title", "value1");
        data.put("timestamp", "2018-10-11");
        data.put("is_background", false);
        data.put("message", "message");
        data.put("image", "image");
    //    data.put("payload", notification);
       
                
        body.put("data", data);
        
        
        log.info("json sent "+body.toString());

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<FirebaseResponse> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            FirebaseResponse firebaseResponse = pushNotification.get();
            if (firebaseResponse.getSuccess() == 1) {
                log.info("push notification sent ok!");

                log.info("response "+firebaseResponse.toString());

            } else {
                log.error("error sending push notifications: " + firebaseResponse.toString());
            }
            return new ResponseEntity<>(firebaseResponse.toString(), HttpStatus.OK);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("the push notification cannot be send.", HttpStatus.BAD_REQUEST);
    }
    
    
    

	@RequestMapping(value = { "/getTest" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage getTest() {

		ErrorMessage res = new ErrorMessage();

		try {

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("Authorization", "key=" + androidFcmKey);
			httpHeaders.set("Content-Type", "application/json");

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss");

			Date date = new Date();
			System.out.println(new Timestamp(date.getTime()));

			/*JSONObject payload = new JSONObject();
			payload.put("team", "india");
			payload.put("score", "5.6");

			JSONObject data = new JSONObject();

			data.put("title", "Hello");
			data.put("is_background", false);
			data.put("message", "Hello MSG");
			data.put("image", "");
			data.put("payload", payload);
			data.put("timestamp", "2018-10-10 7:35:23");

			JSONObject json = new JSONObject();

			json.put("data", data);
			json.put("to", deviceToken);

			System.out.println("Sending :" + json.toString());*/
						
			
			Payload payload=new Payload();
			payload.setScore("5.9");
			payload.setTeam("India");
			
			
			Data  data =new Data();
			
			data.setTitle("hello");
			data.setImage("");
			data.setIs_background(false);
			data.setMessage("message");
		//	data.setPayload(payload);
			data.setTimestamp("2018-10-10");
			
			
			System.out.println("Sending :" + data.toString());


			HttpEntity<Data> httpEntity = new HttpEntity<Data>(data, httpHeaders);
		//	String response = restTemplate.postForObject(androidFcmUrl, httpEntity, String.class);
			//System.out.println(response);
	
		       
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return res;
	}

}
