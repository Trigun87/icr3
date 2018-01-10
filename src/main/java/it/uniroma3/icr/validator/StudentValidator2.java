package it.uniroma3.icr.validator;

import org.springframework.ui.Model;

import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.model.StudentSocial;



public class StudentValidator2 {
	
	public static boolean validate(StudentSocial student, Model model,StudentSocial u,Administrator a) {
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
