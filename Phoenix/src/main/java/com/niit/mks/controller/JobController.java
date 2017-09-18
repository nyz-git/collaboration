package com.niit.mks.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.mks.dao.JobDAO;
import com.niit.mks.model.Job;

@RestController
public class JobController {
	
	@Autowired
	Job job;
	
	@Autowired
	JobDAO jobDAO;
	public JobController()
	{
		System.out.println("Instantiating JobController");
	}
	
	
	
	@RequestMapping(value = "/job/list", method = RequestMethod.GET)
	public ResponseEntity<List<Job>> listAlljobs() {
		List<Job> jobs = jobDAO.list();
		if (jobs.isEmpty()) {
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("No jobs present.");
			jobs.add(job);
		}
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}

	@RequestMapping(value = "/job/get/{jobId}", method = RequestMethod.GET)
	public ResponseEntity<Job> getjob(@PathVariable("jobId") int jobId) {
		System.out.println("Fetching job");
		Job job = jobDAO.get(jobId);
		if (job == null) {
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("job does not exist.");
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/job/create", method = RequestMethod.POST)
	public ResponseEntity<Job> createjob(@RequestBody Job currentJob) {

		job = jobDAO.getByJobProfile(currentJob.getJobProfile());
		if (job == null) {
			job = new Job();
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
			Date date = new Date();
			currentJob.setPostDate(date);
			if(jobDAO.saveOrUpdate(currentJob) == false){
				job = new Job();
				job.setErrorCode("404");
				job.setErrorMessage("Failed to register. Please try again.");
			}
			else{
				job.setErrorCode("200");
				job.setErrorMessage("You are registered successfully.");
			}
				
			
		} else {
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("job already present with this jobname.");
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/job/get/{jobId}", method = RequestMethod.PUT)
	public ResponseEntity<Job> updatejob(@PathVariable("jobId") int jobId, @RequestBody Job updatedjob) {
		job = jobDAO.get(jobId);
		if(job == null){
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("Invalid job");
		}
		else{
			
			job.setQualification(updatedjob.getQualification());
			if(jobDAO.saveOrUpdate(job) == false){
				job = new Job();
				job.setErrorCode("404");
				job.setErrorMessage("Failed to update Job.");
			}else{
				job.setErrorCode("200");
				job.setErrorMessage("Job updated successfully.");
			}
			
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/job/delete/{jobId}", method = RequestMethod.DELETE)
    public ResponseEntity<Job> deletejob(@PathVariable("jobId") int jobId) {
	 job = jobDAO.get(jobId);
		if(job == null){
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("Invalid job");
		}
		else{
			if(jobDAO.delete(job)){
				job = new Job();
				job.setErrorCode("200");
				job.setErrorMessage("job deleted successfully.");
			}else{
				job = new Job();
				job.setErrorCode("404");
				job.setErrorMessage("Failed to delete job.");
			}
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
    }

}
