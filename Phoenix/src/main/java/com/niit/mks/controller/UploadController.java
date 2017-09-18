package com.niit.mks.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.niit.mks.dao.UserDAO;
import com.niit.mks.model.Response;

@RestController
@PropertySource("classpath:config.properties")
public class UploadController {
	
	
	@Autowired
	private UserDAO userDAO;

	// this is an absolute path
	@Value("${imageBasePath}")
	private String imageBasePath;

	@PostMapping("/uploadPicture")
	public ResponseEntity<Response> uploadProfilePicture(@RequestParam("file") MultipartFile file,
			@RequestParam("id") int id) {

		String message = null;

		// We would be using the USER_PROFILE as a prefix so that we can use
		// other prefix
		String fileName = "USER_" + id + ".png";

		if (uploadFile(imageBasePath, fileName, file)) {

			// update the picture id in the database table by using userDAO
			userDAO.updateUserProfileId(fileName, id);

			// in the response the filename of the new image will be send
			return new ResponseEntity<Response>(new Response(1, fileName), HttpStatus.OK);
		} else {
			message = "Failed to update the profile picture!";
			return new ResponseEntity<Response>(new Response(0, message), HttpStatus.NOT_FOUND);
		}

	}

	private boolean uploadFile(String directory, String fileName, MultipartFile file) {

		// Create the directory if does not exists
		if (!new File(directory).exists()) {
			new File(directory).mkdirs();
		}

		try {
			file.transferTo(new File(directory + fileName));
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;

	}
	
	//To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }  

}
