package it.uniroma3.icr.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.StudentSocial;
import it.uniroma3.icr.service.impl.AdminFacade;
import it.uniroma3.icr.service.impl.StudentFacade;
import it.uniroma3.icr.service.impl.StudentFacadeSocial;
import it.uniroma3.icr.validator.studentValidator;
import it.uniroma3.icr.validator.StudentValidator2;

@Controller
public class UserController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Facebook facebook;

	@Autowired
	private Google google;

	@Autowired
	private StudentFacade userFacade;

	@Autowired
	private StudentFacadeSocial userFacadesocial;

	@Autowired
	private AdminFacade adminFacade;

	@Qualifier("userValidator")
	private Validator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registrazione(@ModelAttribute Student student, Model model) {

		Map<String, String> schoolGroups = new HashMap<String, String>();
		schoolGroups.put("3", "3");
		schoolGroups.put("4", "4");
		schoolGroups.put("5", "5");
		model.addAttribute("schoolGroups", schoolGroups);

		Map<String, String> schools = setSchools();
		model.addAttribute("schools", schools);
		
		return "registration";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String confirmUser(@ModelAttribute Student student, Model model) {

		Map<String, String> schools = setSchools();
		model.addAttribute("schools", schools);
		
		Map<String, String> schoolGroups = new HashMap<String, String>();
		schoolGroups.put("3", "3");
		schoolGroups.put("4", "4");
		schoolGroups.put("5", "5");
		model.addAttribute("schoolGroups", schoolGroups);

		Student u = userFacade.findUser(student.getUsername());

		Administrator a = adminFacade.findAdmin(student.getUsername());

		if (studentValidator.validate(student, model, u, a)) {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String passwordEncode = passwordEncoder.encode(student.getPassword());
			student.setPassword(passwordEncode);
			model.addAttribute("student", student);
			userFacade.saveUser(student);
			return "registrationRecap";
//			return "users/homeStudent";
			} else {
			return "registration";
		}

	}

	@RequestMapping(value = "/addUserFromFB", method = RequestMethod.POST)
	public String confirmUserFB(@ModelAttribute StudentSocial student, Model model, @Validated Student p,
			BindingResult bindingResult) {

		Map<String, String> schools = setSchools();
		model.addAttribute("schools", schools);

		Map<String, String> schoolGroups = new HashMap<String, String>();
		schoolGroups.put("3", "3");
		schoolGroups.put("4", "4");
		schoolGroups.put("5", "5");
		model.addAttribute("schoolGroups", schoolGroups);

		StudentSocial u = userFacadesocial.findUser(student.getUsername());
		LOGGER.info("FB authorized (student) for "+ student.toString());

		Administrator a = adminFacade.findAdmin(student.getUsername());
		if (u!=null) {
			LOGGER.info("FB authorized (u) for "+ u.toString());
			LOGGER.info("FB validation (u) for "+ u.toString() + " is " +StudentValidator2.validate(student, model, u, a));
		}

		if (StudentValidator2.validate(student, model, u, a)) {
			model.addAttribute("student", student);
			userFacadesocial.saveUser(student);
			model.addAttribute("social", "fb");
			return "registrationRecap";
//			return "users/homeStudent";
		} else {
			return "registrationFacebook";
		}

	}



	@RequestMapping(value = "/addUserFromGoogle", method = RequestMethod.POST)
	public String confirmUserGoogle(@ModelAttribute StudentSocial student, Model model, @Validated Student p,
			BindingResult bindingResult) {

		Map<String, String> schools = setSchools();
		model.addAttribute("schools", schools);
		
		Map<String, String> schoolGroups = new HashMap<String, String>();
		schoolGroups.put("3", "3");
		schoolGroups.put("4", "4");
		schoolGroups.put("5", "5");
		model.addAttribute("schoolGroups", schoolGroups);

		StudentSocial u = userFacadesocial.findUser(student.getUsername());
		LOGGER.info("Goo authorized (student) for "+ student.toString());

		Administrator a = adminFacade.findAdmin(student.getUsername());
		if (u!=null) {
			LOGGER.info("Goo authorized (u) for "+ u.toString());
			LOGGER.info("Goo validation (u) for "+ u.toString() + " is " +StudentValidator2.validate(student, model, u, a));
		}

		if (StudentValidator2.validate(student, model, u, a)) {
			model.addAttribute("student", student);
			userFacadesocial.saveUser(student);
			model.addAttribute("social", "goo");
			return "registrationRecap";
//			return "users/homeStudent";

		} else {
			return "registrationGoogle";
		}

	}

	@RequestMapping(value = "user/toChangeStudentPassword")
	public String toChangePassword(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Student student = userFacade.findUser(username);
		student.setPassword("");
		model.addAttribute("student", student);
		return "users/changeStudentPassword";

	}

	@RequestMapping(value = "user/changeStudentPassword", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute Student student, Model model) {
		if (student.getPassword().equals("") || student.getPassword() == null) {
			model.addAttribute("errPassword", "*Campo Obbligatorio");
			model.addAttribute("student", student);
			return "users/changeStudentPassword";
		}

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordEncode = passwordEncoder.encode(student.getPassword());
		student.setPassword(passwordEncode);
		userFacade.saveUser(student);
		return "users/homeStudent";
	}

	@RequestMapping(value = "/user/homeStudent")
	public String toHomeStudent(@ModelAttribute Student student, Model model, @ModelAttribute("social") String social) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		if (social.isEmpty())
			student = userFacade.findUser(username);
		else
			student = userFacadesocial.findUser(username);
		model.addAttribute("student", student);
		model.addAttribute("social", social);
		return "users/homeStudent";
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
	
	/*
	 * @RequestMapping(value = "/user/homeStudentSocial") public String
	 * toHomeStudentSocial(@ModelAttribute StudentSocial student, Model model,
	 * 
	 * @ModelAttribute("social") String social) { Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication(); String username =
	 * auth.getName(); student = userFacadesocial.findUser(username);
	 * model.addAttribute("student", student);
	 * 
	 * model.addAttribute("social", social);
	 * 
	 * return "users/homeStudentSocial"; }
	 */

}
