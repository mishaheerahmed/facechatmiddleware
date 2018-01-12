package com.facechat.facechatmiddleware.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.facechat.facechatbackend.dao.JobDAO;
import com.facechat.facechatbackend.model.Job;

@RestController
public class JobController 
{
@Autowired
JobDAO jobDAO;

@PostMapping(value="/insertjob")
public ResponseEntity<String> insertjob(@RequestBody Job jobs)
{
	jobs.setPostdate(new java.util.Date());
	System.out.println("Inserting Job");
	if(jobDAO.addJob(jobs))
	{
	return new ResponseEntity<String>("Successfully insert",HttpStatus.OK);
	}
	else
	{
		return new ResponseEntity<String>("Problem inserting",HttpStatus.INTERNAL_SERVER_ERROR);

	}
}

@GetMapping(value="/deletejob/{jobId}")
public ResponseEntity<String> deletejob(@PathVariable("jobId") int jobId)
{
	Job jobs=jobDAO.getJob(jobId);
	if(jobDAO.deleteJob(jobs))
	{
		return new ResponseEntity<String>("Successfully Deleted",HttpStatus.OK);	
	}
	else
	{
		return new ResponseEntity<String>("Problem Deleting",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@GetMapping(value="/getAllJobs")
public ResponseEntity<List<Job>> getAllJobs()
{
	List<Job> jobslist=jobDAO.getAllJob();
	return new ResponseEntity<List<Job>>(jobslist,HttpStatus.OK);
}


}//class close