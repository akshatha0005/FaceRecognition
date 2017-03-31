package com.atithi.ImageSender;

import java.beans.Customizer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.james.mime4j.field.ContentTypeField;

import com.google.gson.Gson;

public class RestRequestSender {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		HttpClient myClient = new DefaultHttpClient();
		Customer cust = new Customer();
		cust.setName("CUSTOMER2");
		cust.setPass("CUSTOMER2");
		
		UserDetails details = new UserDetails();
		details.setName("RASP1");
		details.setPass("RASP1");
		cust.setDetails(details);
	    Gson gson = new Gson();
	    String jsonString =gson.toJson(cust);
		
		HttpPost post = new HttpPost("http://localhost:8081/ImageManager/rest/dbService/storeCustomer");
		StringEntity entity = new StringEntity(jsonString);
		post.setEntity(entity);
		entity.setContentType("application/json");
		HttpResponse resp = myClient.execute(post);
		System.out.println("Status: "+ resp.getStatusLine());
	}
	




}
