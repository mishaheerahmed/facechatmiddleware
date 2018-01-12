package com.facechat.facechatmiddleware.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.facechat.facechatbackend.dao.ProfilePictureDAO;
import com.facechat.facechatbackend.model.ProfilePicture;



@RestController
public class ProfilePictureController 
{

	@Autowired
	ProfilePictureDAO profilepictureDAO;
	
	@PostMapping(value="/ProfilePictureUpload")
	public ResponseEntity<?> ProfilePictureUpload(@RequestParam(value="file") CommonsMultipartFile fileUpload,HttpSession session)
	{
		System.out.println("uploading picture");
		//String username=(String)session.getAttribute("username");
		ProfilePicture profilepicture= new ProfilePicture();
	    System.out.println(fileUpload.getBytes());
		System.out.println("picture uploaded");

		profilepicture.setImage(fileUpload.getBytes());
        profilepicture.setUsername("Abc");
		
		profilepictureDAO.save(profilepicture);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping(value="/getImage/{username}")
	public @ResponseBody byte[] getProfilePicture(@PathVariable("username")String username, HttpStatus session)
	{
		System.out.println("Username: " + username);
		ProfilePicture profilepicture=profilepictureDAO.getProfilePicture(username);
		return profilepicture.getImage();
    }
}