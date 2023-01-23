package com.example.nwtktsapi.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.example.nwtktsapi.dto.ImgbbResponse;
import com.google.gson.Gson;


@Service
public class ImageService {

	private final String API_KEY = "507c7dea7d51715a852fba89fd5eabb2";
	private final HttpClient httpClient = HttpClientBuilder.create().build();
	
	public String uploadImage(InputStream is) throws IOException {
        HttpPost httpPost = new HttpPost("https://api.imgbb.com/1/upload");
        httpPost.addHeader("X-API-KEY", API_KEY);

        HttpEntity entity = MultipartEntityBuilder.create()
        		.addTextBody("key", API_KEY)
                .addBinaryBody("image", is, ContentType.APPLICATION_OCTET_STREAM, "image.jpg")
                .build();

        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        String responseString = EntityUtils.toString(response.getEntity());
        
        Gson gson = new Gson();
        ImgbbResponse imgbbResponse = gson.fromJson(responseString, ImgbbResponse.class);
        String imageUrl = imgbbResponse.getData().getUrl();
        return imageUrl;
    }
}
