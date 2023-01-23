package com.example.nwtktsapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.nwtktsapi.dto.ImageUrlDTO;
import com.example.nwtktsapi.service.ImageService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/images")
public class ImageController {
	
	@Autowired
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
    	String imageUrl = imageService.uploadImage(image.getInputStream());
    	return new Gson().toJson(new ImageUrlDTO(imageUrl));
    }

}
