package com.facechat.facechatmiddleware.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facechat.facechatbackend.dao.UserDAO;
import com.facechat.facechatbackend.model.UserDetail;


@RestController
public class UserController {
	@Autowired
	UserDAO userDAO;
	
	@PostMapping(value="/register")
	public ResponseEntity<String> registeruser(@RequestBody UserDetail user)
	{
	System.out.println("Registration");
	System.out.println("Email_ID"+user.getEmailID());

	user.setRole("USER");

	user.setIsOnline("N");
	if(userDAO.addUser(user))	
	{
		return new ResponseEntity<String>("Registered",HttpStatus.OK);
	}
	else
	{
		return new ResponseEntity<String>("error in register",HttpStatus.INTERNAL_SERVER_ERROR);

	}
	}	
	
	@PostMapping(value="/login")
	public ResponseEntity<UserDetail> checklogin(@RequestBody UserDetail user,HttpSession session)
	{
		System.out.println("User: " + user);
	if (userDAO.checkLogin(user))
	{
		System.out.println("logging");
		//userDAO.updateOnlineStatus("Y", user);
		UserDetail tempUser = userDAO.getUser(user.getUsername());
		//tempUser.setIsOnline("Y");
		userDAO.updateOnlineStatus("Y", tempUser);
		session.setAttribute("username", user.getUsername());
		System.out.println("login success");
		return new ResponseEntity<UserDetail>(tempUser, HttpStatus.OK);
	}
	else 
	{
		return new ResponseEntity<UserDetail>(user, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	@RequestMapping(value="/getAllUsers")
	public ResponseEntity<ArrayList<UserDetail>> getAllUsers()
	{
		ArrayList listUsers = (ArrayList)userDAO.getAllUsers();
		return new ResponseEntity<ArrayList<UserDetail>>(listUsers,HttpStatus.OK);
	}
	
	@GetMapping(value="/logout/{username}")
	public ResponseEntity<String> logout(@PathVariable("username") String username)
	{
		UserDetail user=userDAO.getUser(username);
		if(userDAO.updateOnlineStatus("N", user))
		{
			return new ResponseEntity<String>(" logged out Successfully",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Error logged",HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
}
