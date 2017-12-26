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

//PAOLO	private Facebook facebook;
	private ConnectionRepository connectionRepository;
	@Autowired
	private StudentFacadeSocial userFacadesocial;

	public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
//PAOLO		this.facebook = facebook;
		this.connectionRepository = connectionRepository;

	}

	@RequestMapping(value = "/facebookLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String helloFacebook(@RequestParam(value = "daFB", required = false) String daFB, Model model,
			@ModelAttribute("social") String social, RedirectAttributes redirectAttributes) {
//PAOLO ADD NEXT LINE
		Facebook facebook = connectionRepository.findPrimaryConnection(Facebook.class).getApi();
		
		if (daFB == null)
			return "redirect:/login";

		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return "redirect:/connect/facebook";
		}

		
//		String[] fields = { "name", "email" };
		String[] fields = { "first_name", "last_name", "email" };

		
		User user = facebook.fetchObject("me", User.class, fields);
//		User user = facebook.fetchObject("me", User.class);


		String email = user.getEmail();
		String id = user.getId();

//		StudentSocial student = userFacadesocial.findUser(email);
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
		    LOGGER.info(student.toString() +" logged in");
			social = "fb";
			redirectAttributes.addFlashAttribute("social", social);
			return "redirect:/user/homeStudent";
		}

//		String userprofile = user.getName();
//		String[] temp;
//		String delimiter = " ";
//		temp = userprofile.split(delimiter);
//		String name = temp[0];
//		String surname = temp[1];
		String name = user.getFirstName();
		String surname = user.getLastName();
//		String email = user.getEmail();
		
		model.addAttribute("nome", name);
		model.addAttribute("cognome", surname);
		model.addAttribute("email", email);
		model.addAttribute("id", id);
		model.addAttribute("student", new StudentSocial());

		Map<String, String> schoolGroups = new HashMap<String, String>();
		schoolGroups.put("3", "3");
		schoolGroups.put("4", "4");
		schoolGroups.put("5", "5");
		model.addAttribute("schoolGroups", schoolGroups);
		return "/registrationFacebook";
	}

}