package com.facechat.facechatmiddleware.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.facechat.facechatbackend.dao.FriendDAO;
import com.facechat.facechatbackend.dao.UserDAO;
import com.facechat.facechatbackend.model.Friend;
import com.facechat.facechatbackend.model.UserDetail;


@RestController
public class FriendController
{

@Autowired
 FriendDAO friendDAO;
 
@Autowired
UserDAO userDAO;

@PostMapping(value="/createfriendrequest")
 public ResponseEntity<String> createfriendrequest(@RequestBody Friend friend)
 {
	 friend.setStatus("R");
	 if(friendDAO.createFriend(friend))
	 {
		 return new ResponseEntity<String>("Success",HttpStatus.OK);
	 }
	 else
	 {
		 return new ResponseEntity<String>("failure",HttpStatus.INTERNAL_SERVER_ERROR);
	 }
 }
 
 @GetMapping(value="/approvalfriendrequest/{friendId}")
 public ResponseEntity<String> approvalfriendrequest(@PathVariable("friendId") int friendId)
 {
	 Friend friend=friendDAO.getFriend(friendId);
	 
	 if(friendDAO.approveFriendRequest(friend))
	 {
		 return new ResponseEntity<String>(" approve Success",HttpStatus.OK);
	 }
	 else
	 {
		 return new ResponseEntity<String>("failure in approval",HttpStatus.INTERNAL_SERVER_ERROR);

	 }
 }
 
 
 @GetMapping(value="/rejectfriendrequest/{friendId}")
 public ResponseEntity<String> rejectfriendrequest(@PathVariable("friendId") int friendId)
 {
	 Friend friend=friendDAO.getFriend(friendId);
	 
	 if(friendDAO.rejectFriendRequest(friend))
	 {
		 return new ResponseEntity<String>(" reject Success",HttpStatus.OK);
	 }
	 else
	 {
		 return new ResponseEntity<String>("failure in reject",HttpStatus.INTERNAL_SERVER_ERROR);

	 }
 }
 
 @GetMapping(value="/getapprovefriends/{userName}")
	public ResponseEntity<List<Friend>> getapprovefriends(@PathVariable("userName") String userName)
	{
		System.out.println("obj**"+friendDAO);
		List<Friend> listfriends = (ArrayList) friendDAO.getApprovedFriends(userName);
		return new ResponseEntity<List<Friend>>(listfriends,HttpStatus.OK);
	}
 
 @GetMapping(value="/getfriend/{friendid}")
	public ResponseEntity<Friend> acceptfriend(@PathVariable("friendid") int friendid)
	{
		Friend b=friendDAO.getfriendbyid(friendid);
		if(b!=null)
		 {
		    System.out.println(b);
		    
	        return new ResponseEntity<Friend>(b,HttpStatus.OK);
	      }
		 else
	      {
		   return new ResponseEntity<Friend>(b,HttpStatus.INTERNAL_SERVER_ERROR);	
	      }
	    
}
 
@GetMapping(value="/getAllFriendRequest/{userName}")
public ResponseEntity<List<Friend>> getAllFriendRequest(@PathVariable("userName") String userName)
{
	System.out.println("obj**"+friendDAO);
	List<Friend> listfriends = (ArrayList) friendDAO.getAllFriendRequest(userName);
	return new ResponseEntity<List<Friend>>(listfriends,HttpStatus.OK);
}
 
@GetMapping(value="/getAllusers/{username}")
public ResponseEntity<List> getalluser(@PathVariable("username") String username)
{
	UserDetail u=userDAO.getUser(username);
	System.out.println("obj**"+friendDAO);
	List listfriends = (ArrayList) userDAO.getalluser1(u);
	return new ResponseEntity<List>(listfriends,HttpStatus.OK);
}
@GetMapping(value="/deletefriend/{friendId}")
public ResponseEntity<Friend> deletefriend(@PathVariable("friendId") int friendId)
{
	//System.out.println("obj**"+friendDAO);
	Friend f=friendDAO.getfriendbyid(friendId);
	f.setStatus("R");
	if(friendDAO.deletefriend(f))
	{
	return new ResponseEntity<Friend>(f,HttpStatus.OK);
    }
	else
	{
		return new ResponseEntity<Friend>(f,HttpStatus.INTERNAL_SERVER_ERROR);	
	}
}
}//class close