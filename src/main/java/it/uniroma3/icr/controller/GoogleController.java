package it.uniroma3.icr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.StudentSocial;
import it.uniroma3.icr.service.impl.StudentFacadeSocial;




@Controller

public class GoogleController {

	
    private Google google;
    private ConnectionRepository connectionRepository;
    @Autowired
	private StudentFacadeSocial userFacadesocial;

    public GoogleController(Google google, ConnectionRepository connectionRepository) {
        this.google = google;
        this.connectionRepository = connectionRepository;
        
    }

    
    @RequestMapping(value="/googleLogin", method = {RequestMethod.GET, RequestMethod.POST})
    public String helloGoogle(@RequestParam(value = "daGoogle", required = false)String daGoogle, Model model,
    		@ModelAttribute("social") String social,RedirectAttributes redirectAttributes) {
       
    	if(daGoogle==null)
    		return "redirect:/login";
    	
    	if (connectionRepository.findPrimaryConnection(Google.class) == null) {
            return "redirect:/connect/google";
        }

        String email=google.userOperations().getUserInfo().getEmail();
       
        StudentSocial student= userFacadesocial.findUser(email);
        if(student!=null){
        	SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
            List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
            updatedAuthorities.add(authority);
            
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            												student.getUsername(),"",updatedAuthorities);
            auth.setDetails(student); 
            SecurityContextHolder.getContext().setAuthentication(auth);
        	model.addAttribute("student", student);
        	social="goo";
        	redirectAttributes.addFlashAttribute("social", social);
        	return "redirect:/user/homeStudentSocial";
        }
        else{
        
        
        String userprofile=google.userOperations().getUserInfo().getName();
        if(userprofile.equals(""))
        	return "users/noGooglePlus";
        String[] temp;
		String delimiter = " ";
		temp = userprofile.split(delimiter);   
		String name = temp[0];   
		String surname = temp[1];
		model.addAttribute("nome", name);
		model.addAttribute("cognome", surname);
		model.addAttribute("email", email);
		model.addAttribute("student", new Student());
		
		Map<String,String> schoolGroups = new HashMap<String,String>();
		schoolGroups.put("3", "3");
		schoolGroups.put("4", "4");
		schoolGroups.put("5", "5");
		model.addAttribute("schoolGroups", schoolGroups);
       return "/registrationGoogle";
        }
    }
}
