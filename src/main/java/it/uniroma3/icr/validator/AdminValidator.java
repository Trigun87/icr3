package it.uniroma3.icr.validator;

import org.springframework.ui.Model;

import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.model.Student;


public class AdminValidator {
	
	public static boolean validate(Administrator admin, Model model,Administrator a,Student s) {
		boolean verifica=true;
		
		if(admin.getUsername().equals("")){
			verifica=false;
			model.addAttribute("errUsername","*Campo obbligatorio");
		}
		if(admin.getPassword().equals("")){
			verifica=false;
			model.addAttribute("errPassword","*Campo obbligatorio");
		}
		
		if(a!=null || s!=null) {
			verifica=false;
			model.addAttribute("usernameError","Username gia' esistente");
		
		}
		
		
	return verifica;
}
}