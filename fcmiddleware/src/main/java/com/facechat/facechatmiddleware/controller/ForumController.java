package com.facechat.facechatmiddleware.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facechat.facechatbackend.dao.ForumDAO;
import com.facechat.facechatbackend.model.Forum;

@RestController
public class ForumController {
	@Autowired
	ForumDAO forumDAO;
	
	@RequestMapping(value ="/insertForum")
	public ResponseEntity<String> insertForum(@RequestBody Forum Forum)
	{		
		if(forumDAO.addForum(Forum))
		{
			return new ResponseEntity<String>("Forum Added",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Error in Response Entity",HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@RequestMapping(value="/updateForum")
	public  ResponseEntity<String> updateForum(@RequestBody Forum Forum)
	{
		Forum tempForum=forumDAO.getForum(Forum.getForumId());
		
		
		
		tempForum.setForumName(Forum.getForumName());
		tempForum.setForumContent(Forum.getForumContent());
		if(forumDAO.updateForum(tempForum))
		{
			return new ResponseEntity<String>("Forum Update",HttpStatus.OK);
			
		}
		else
		{
			return new ResponseEntity<String>("Error in Forum updation",HttpStatus.SERVICE_UNAVAILABLE);
			
		}
	}
	@RequestMapping(value="/getAllForums")
	public ResponseEntity<ArrayList<Forum>> getAllForums()
	{
		ArrayList listForums = (ArrayList)forumDAO.getAllForums();
		return new ResponseEntity<ArrayList<Forum>>(listForums,HttpStatus.OK);
	}
	
	@GetMapping(value="/deleteForum/{forumId}")
	public ResponseEntity<String> deleteform(@PathVariable("forumId")int forumId)
	{
		Forum tempforum=forumDAO.getForum(forumId);
		if(forumDAO.deleteForum(tempforum))
		{
			return new ResponseEntity<String>("forum deleted",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("forum deleted",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
