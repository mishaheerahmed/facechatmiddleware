package com.facechat.facechatmiddleware.controller;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facechat.facechatbackend.dao.BlogDAO;
import com.facechat.facechatbackend.model.Blog;

@RestController
public class BlogController {

	@Autowired
	BlogDAO blogDAO;
	
	@RequestMapping(value ="/insertBlog")
	public ResponseEntity<String> insertBlog(@RequestBody Blog blog)
	{		
		if(blogDAO.addBlog(blog))
		{
			blog.setStatus("N");
			return new ResponseEntity<String>("Blog Added",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Error in Response Entity",HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@GetMapping(value="/approveBlog/{blogid}")
	public ResponseEntity<String> approveBlog(@PathVariable("blogid") int blogid)
	{
		Blog tempblog=blogDAO.getBlog(blogid);
		if(blogDAO.approveBlog(tempblog))
		{
			return new ResponseEntity<String>("Blog Approved",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Error in BLog Approval",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/rejectBlog/{blogid}")
	public ResponseEntity<String> rejectBlog(@PathVariable("blogid") int blogid)
	{
		Blog tempblog=blogDAO.getBlog(blogid);
		if(blogDAO.rejectBlog(tempblog))
		{
			return new ResponseEntity<String>("Blog Rejected",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Error in BLog Rejection",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value="/updateBlog")
	public  ResponseEntity<String> updateBlog(@RequestBody Blog blog)
	{
		Blog tempBlog=blogDAO.getBlog(blog.getBlogId());
		
		
		
		tempBlog.setBlogName(blog.getBlogName());
		tempBlog.setBlogContent(blog.getBlogContent());
		if(blogDAO.updateBlog(tempBlog))
		{
			return new ResponseEntity<String>("Blog Update",HttpStatus.OK);
			
		}
		else
		{
			return new ResponseEntity<String>("Error in Blog updation",HttpStatus.SERVICE_UNAVAILABLE);
			
		}
	}
	@RequestMapping(value="/getAllBlogs")
	public ResponseEntity<ArrayList<Blog>> getAllBlogs()
	{
		ArrayList listBlogs = (ArrayList)blogDAO.getAllBlogs();
		return new ResponseEntity<ArrayList<Blog>>(listBlogs,HttpStatus.OK);
	}
	@GetMapping("/deleteBlog/{blogId}")
	public ResponseEntity<String> deleteBlog(@PathVariable("blogId") int blogId) 
	{
		Blog tempblog = blogDAO.getBlog(blogId);
		System.out.println("deletion in blog");
		if (blogDAO.deleteBlog(tempblog)) 
		{			
			return new ResponseEntity<String>("Blog deleted", HttpStatus.OK);
		} 
		else
		{
			return new ResponseEntity<String>("problem deleting blog", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/incLike/{blogId}")
	public ResponseEntity<String> incrementLike(@PathVariable("blogId") int blogId)
	{
		Blog tempblog=blogDAO.getBlog(blogId);
		if(blogDAO.incrementLike(tempblog))
		{
			return new ResponseEntity<String>("like incremented",HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>("error in like increment",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	}
