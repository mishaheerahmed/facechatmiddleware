package com.facechat.facechatmiddleware.controller;

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
import com.facechat.facechatbackend.model.Friend;


@RestController
public class FriendController
{

@Autowired
 FriendDAO friendDAO;
 
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
 
@GetMapping(value="/getAllFriendRequest")
public ResponseEntity<List<Friend>> getAllFriendRequest(HttpSession session)
{
	String currentUser=(String)session.getAttribute("currentUser");
	currentUser="shaheer";
	System.out.println("Current user:"+ currentUser);
	List<Friend> listfriendrequests=friendDAO.getAllFriendRequest(currentUser);
	return new ResponseEntity<List<Friend>>(listfriendrequests,HttpStatus.OK);
}
 
 
}//class close