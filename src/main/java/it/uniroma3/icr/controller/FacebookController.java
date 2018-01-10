package it.uniroma3.icr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.icr.model.StudentSocial;
import it.uniroma3.icr.service.impl.StudentFacadeSocial;

@Controller

public class FacebookController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private ConnectionRepository connectionRepository;
	@Autowired
	private StudentFacadeSocial userFacadesocial;

	public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
	}

	@RequestMapping(value = "/facebookLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String helloFacebook(@RequestParam(value = "daFB", required = false) String daFB, Model model,
			@ModelAttribute("social") String social, RedirectAttributes redirectAttributes) {

		Facebook facebook = connectionRepository.findPrimaryConnection(Facebook.class).getApi();
		
		if (daFB == null)
			return "redirect:/login";

		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return "redirect:/connect/facebook";
		}

		String[] fields = { "first_name", "last_name", "email" };

		
		User user = facebook.fetchObject("me", User.class, fields);

		String email = user.getEmail();
		String id = user.getId();

		StudentSocial student = userFacadesocial.findUser(id);

		if (student != null) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
			List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
			updatedAuthorities.add(authority);

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(student.getUsername(),
					"", updatedAuthorities);
			auth.setDetails(student);
			SecurityContextHolder.getContext().setAuthentication(auth);
			model.addAttribute("student", student);
		    LOGGER.info("Login: " + student.toString());
			social = "fb";
			redirectAttributes.addFlashAttribute("social", social);
			return "redirect:/user/homeStudent";
		}

		String name = user.getFirstName();
		String surname = user.getLastName();
		
		model.addAttribute("nome", name);
		model.addAttribute("cognome", surname);
		model.addAttribute("email", email);
		model.addAttribute("id", id);
		model.addAttribute("student", new StudentSocial());
		
		Map<String, String> schools = setSchools();
		model.addAttribute("schools", schools);

		Map<String, String> schoolGroups = new HashMap<String, String>();
		schoolGroups.put("3", "3");
		schoolGroups.put("4", "4");
		schoolGroups.put("5", "5");
		model.addAttribute("schoolGroups", schoolGroups);
		return "/registrationFacebook";
	}
	
	private Map<String, String> setSchools() {
		Map<String, String> schools = new HashMap<>();
		schools.put("Anco Marzio","Anco Marzio");
		schools.put("Aristofane","Aristofane");
		schools.put("Aristotele","Aristotele");
		schools.put("Augusto","Augusto");
		schools.put("C.Cavour","C.Cavour");
		schools.put("Croce Aleramo","Croce Aleramo");
		schools.put("Democrito","Democrito");
		schools.put("Ettore Majorana","Ettore Majorana");
		schools.put("Farnesina","Farnesina");
		schools.put("Giordano Bruno","Giordano Bruno");
		schools.put("Giulio Cesare","Giulio Cesare");
		schools.put("Giuseppe Peano","Giuseppe Peano");
		schools.put("Guidonia Montecelio","Guidonia Montecelio");
		schools.put("IIS via Albergotti 35","IIS via Albergotti 35");
		schools.put("Istituto Minerva","Istituto Minerva");
		schools.put("Kennedy","Kennedy");
		schools.put("Keplero","Keplero");
		schools.put("Luciano Manara","Luciano Manara");
		schools.put("Massimiliano Massimo","Massimiliano Massimo");
		schools.put("Primo Levi","Primo Levi");
		schools.put("Sandro Pertini","Sandro Pertini");
		schools.put("Tacito","Tacito");
		schools.put("Volontario esterno","Volontario esterno");
		return schools;
	}

}