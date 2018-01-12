package it.uniroma3.icr.validator;

import org.springframework.ui.Model;

import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.model.Student;



public class studentValidator {
	
	public static boolean validate(Student student, Model model,Student u,Administrator a) {
		boolean verifica=true;
		
		
		if(student.getName().equals("")){
			verifica=false;
			model.addAttribute("errName","*Campo obbligatorio");
		}
		if(student.getSurname().equals("")){
			verifica=false;
			model.addAttribute("errSurname","*Campo obbligatorio");
		}
		if(student.getSchool().equals("")){
			verifica=false;
			model.addAttribute("errSchool","*Campo obbligatorio");
		}
		if(student.getSection().equals("")){
			verifica=false;
			model.addAttribute("errSection","*Campo obbligatorio");
		}
		if(student.getUsername().equals("")){
			verifica=false;
			model.addAttribute("errUsername","*Campo obbligatorio");
		}
		if(student.getPassword().equals("")){
			verifica=false;
			model.addAttribute("errPassword","*Campo obbligatorio");
		}
		if(student.getEmail().equals("")){
			verifica=false;
			model.addAttribute("errEmail","*Campo obbligatorio");
		}
		
		if(u!=null || a!=null) {
			verifica=false;
			model.addAttribute("usernameError","Username gia' esistente");
		}
		
		return verifica;
	}

}
