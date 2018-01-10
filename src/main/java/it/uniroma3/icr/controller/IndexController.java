package it.uniroma3.icr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController {


	@RequestMapping(value="/",  method = RequestMethod.GET)
	public String goToIndex() {
		return "login";
		
	}
	
	@RequestMapping(value="/login_admin", method=RequestMethod.GET)
	public String goToLoginAdmin(){
		return "login_admin";
	}
	
	@RequestMapping(value="/login_studente", method=RequestMethod.GET)
	public String goToLoginStudente(){
		return "login_admin";
	}
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String goToIndex1(){
		return "login";
	}
}
