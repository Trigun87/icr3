package it.uniroma3.icr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.service.impl.StudentFacade;
import it.uniroma3.icr.service.impl.TaskFacade;

@Controller
public class LoginController  {

	@Autowired
	public TaskFacade taskFacade;

	@Autowired
	public StudentFacade studentFacade;

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login (ModelMap model,@RequestParam(value = "error", required = false) String error) {

		if (error != null) {
			model.addAttribute("error", "Username o password non validi");
		}
		model.addAttribute("user", new Student());
		model.addAttribute("admin", new Administrator());
		
		
		return "login";

	}

	
	
	@RequestMapping(value="*/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}
	
	/*@RequestMapping(value="/logout2", method = RequestMethod.GET)
	public String logoutPage2 (HttpServletRequest request, HttpServletResponse response) {
	    
	    return "redirect:/login?logout";
	}*/
	

	@RequestMapping(value="/role", method = RequestMethod.GET)
	public String loginRole(Model model,HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toString();
		Student student = studentFacade.findUser(auth.getName());
		String targetUrl = "";
		if(role.contains("ROLE_USER")) {
			model.addAttribute("student", student);
			targetUrl = "redirect:/user/homeStudent";
		} else if(role.contains("ROLE_ADMIN")) {
			targetUrl = "redirect:/admin/homeAdmin";
		}

		return targetUrl;
	}
}











