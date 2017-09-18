package com.niit.mks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	public HomeController()
	{
		System.out.println("Instantiating Home Controller");
		
	}
	
	@RequestMapping("/")
	public String home()
	{
		return "index";
	}
}
