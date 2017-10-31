package it.uniroma3.icr.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import it.uniroma3.icr.model.ComparatoreSimboloPerNome;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.NegativeSample;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Sample;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.Symbol;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.model.Word;
import it.uniroma3.icr.service.editor.SymbolEditor;
import it.uniroma3.icr.service.impl.AdminFacade;
import it.uniroma3.icr.service.impl.ImageFacade;
import it.uniroma3.icr.service.impl.JobFacade;
import it.uniroma3.icr.service.impl.ManuscriptService;
import it.uniroma3.icr.service.impl.NegativeSampleService;
import it.uniroma3.icr.service.impl.SampleService;
import it.uniroma3.icr.service.impl.StudentFacade;
import it.uniroma3.icr.service.impl.SymbolFacade;
import it.uniroma3.icr.service.impl.TaskFacade;
import it.uniroma3.icr.service.impl.WordFacade;
import it.uniroma3.icr.validator.AdminValidator;
import it.uniroma3.icr.validator.jobValidator;
import it.uniroma3.icr.view.CorrectStudentsAnswer;
import it.uniroma3.icr.view.MajorityVoting;
import it.uniroma3.icr.view.StudentsProductivity;
import it.uniroma3.icr.view.SymbolsAnswers;
import it.uniroma3.icr.view.TaskTimes;
import it.uniroma3.icr.view.Voting;

@Controller

public class AdminController {

	@Autowired
	private  SymbolEditor symbolEditor;
	@Autowired
	private StudentFacade studentFacade;
	@Autowired
	private AdminFacade adminFacade;
	@Autowired
	private JobFacade facadeJob;
	@Autowired
	private SampleService sampleService;
	@Autowired
	private NegativeSampleService negativeSampleService;
	@Autowired
	private TaskFacade facadeTask;
	@Autowired
	private SymbolFacade symbolFacade;;
	@Autowired
	private ImageFacade imageFacade;
	@Autowired
	private WordFacade wordFacade;
	@Autowired
	private ManuscriptService manuscriptService;

	@Qualifier("adminValidator")
	private Validator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Symbol.class, this.symbolEditor);
		binder.setValidator(validator);
	}
	@RequestMapping(value="admin/homeAdmin")
	public String toHomeAdmin() {
		return"administration/homeAdmin";
	}

	/*--------------------------------------------REGISTRA ADMIN------------------------------------------------------------------------*/

	@RequestMapping(value="admin/registrationAdmin", method=RequestMethod.GET)
	public String registration(@ModelAttribute Administrator administrator, Model model) {
		return "administration/registrationAdmin";
	}

	@RequestMapping(value="admin/addAdmin", method = RequestMethod.POST)
	public String confirmAdmin(@ModelAttribute Administrator administrator, @Validated Administrator a,
			BindingResult bindingResult,Model model) {
		
		Administrator a1= adminFacade.findAdmin(administrator.getUsername());
		Student s1= studentFacade.findUser(administrator.getUsername());
		
		
		if(AdminValidator.validate(administrator,model,a1,s1)){
		/*PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordEncode = passwordEncoder.encode(administrator.getPassword());
		administrator.setPassword(passwordEncode);*/
		administrator.setPassword(administrator.getPassword());
		model.addAttribute("administrator", administrator);
		adminFacade.addAdmin(administrator);
		return "administration/adminRecap";}
		
		else{
			return "administration/registrationAdmin";
		}

	}
	/*--------------------------------------------INSERISCI JOB------------------------------------------------------------------------*/
	
	@RequestMapping(value="admin/toSelectManuscript")
	private String toSelectManuscript(@ModelAttribute Job job, @ModelAttribute Manuscript manuscript, @ModelAttribute Task task, Model model) {

		List<Manuscript> listManuscripts = this.manuscriptService.findAllManuscript();
		if(listManuscripts.size()==0)
			return "administration/noManuscriptForJob";

		List<String> listManuscriptsName = new ArrayList<>();
		for(Manuscript m : listManuscripts){
			listManuscriptsName.add(m.getName());
		}
		
		model.addAttribute("manuscripts", listManuscriptsName);
		model.addAttribute("job", job);
		model.addAttribute("task", task);
		return "administration/selectImageByManuscript";
	}


	@RequestMapping(value="admin/selectImageByManuscript")
	private String newJobByManuscript(HttpSession session, @ModelAttribute("manuscript") Manuscript manuscript, @ModelAttribute Job job,@ModelAttribute Task task, Model model) {
		String manuscriptName = manuscript.getName();
		List<Symbol> symbols = symbolFacade.findSymbolByManuscriptName(manuscriptName);
		Collections.sort(symbols, new ComparatoreSimboloPerNome());	
		job.setManuscript(manuscript);
		session.setAttribute("manuscript", manuscript);
		model.addAttribute("symbols", symbols);
		model.addAttribute("job", job);
		return "administration/insertJobByManuscript";

	}

	@RequestMapping(value="admin/addJobByManuscript")
	public String confirmJobByManuscript(HttpSession session, HttpServletRequest request, @Valid @ModelAttribute Job job, 
	BindingResult bindingResult, @ModelAttribute Task task, @ModelAttribute Image image, @ModelAttribute Result result, Model model ){
		Manuscript manuscript = manuscriptService.findManuscriptByName(((Manuscript) session.getAttribute("manuscript")).getName());
		model.addAttribute("job",job);
		model.addAttribute("task",task);
		model.addAttribute("manuscript", manuscript);
		Integer number = 5;
		Boolean bool = false;
		List<Word> jobWords = null;
		List<Image> imagesTask = null;
		if(number!=null){
		if(manuscript.getWords()!=null){

			jobWords = this.wordFacade.getWordsForManuscriptName(manuscript.getName(), number);

			bool=true;
		}else{ 
			imagesTask = this.imageFacade.getImagesForTypeAndManuscriptName(job.getSymbol().getType(), manuscript.getName(),number);
			job.setImages(imagesTask);

		}
		}
		
		 if(jobValidator.validate(job,model)){
		this.facadeJob.createJob(job, manuscript, jobWords, imagesTask, bool, number,task);
		return "administration/jobRecap";}
		else{
			String manuscriptName = manuscript.getName();
			List<Symbol> symbols = symbolFacade.findSymbolByManuscriptName(manuscriptName);
			Collections.sort(symbols, new ComparatoreSimboloPerNome());	
			job.setManuscript(manuscript);
			session.setAttribute("manuscript", manuscript);
			model.addAttribute("symbols", symbols);
			
			return "administration/insertJobByManuscript";
		}
	}
	
	
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------*/
	/*--------------------------------------------INSERISCI MANOSCRITTO DB--------------------------------------------------------------------------------*/
	@RequestMapping(value="admin/selectManuscript")
	public String selectManuscript(Model model, @ModelAttribute Manuscript manuscript) throws FileNotFoundException, IOException{
		List<Manuscript> listManuscripts = this.imageFacade.getManuscript(); // da dove prendo tutti i manoscritti?

		List<String> listManuscriptsName = new ArrayList<>();
		for(Manuscript m : listManuscripts){
			if(manuscriptService.findManuscriptByName(m.getName())== null)
				listManuscriptsName.add(m.getName());
		}
		if(listManuscriptsName.isEmpty()){
			return "administration/noInsertManuscript";
		}else{
			model.addAttribute("manuscripts", listManuscriptsName);
			return "administration/insertManuscript";
		}
	}

	@RequestMapping(value="admin/insertManuscript")
	public String insertManuscript(@ModelAttribute("manuscript") Manuscript manuscript, Model model, HttpServletRequest request,
	@ModelAttribute Symbol symbol,@ModelAttribute Word word, @ModelAttribute Image image, @ModelAttribute Sample sample,
	@ModelAttribute NegativeSample negativeSample) throws FileNotFoundException, IOException{
		
		String manuscriptName = manuscript.getName();
		this.manuscriptService.saveManuscript(manuscript);
		Manuscript m = this.manuscriptService.findManuscriptByName(manuscriptName);
		String path = symbolFacade.getPath();
		path = path.concat(manuscriptName).concat("/");
		symbolFacade.insertSymbolInDb(path, m);
		path = sampleService.getPath();
		path = path.concat(manuscriptName).concat("/");
		sampleService.getSampleImage(path, m);
		path = negativeSampleService.getNegativePath();
		path = path.concat(manuscriptName).concat("/");
		negativeSampleService.getNegativeSampleImage(path, m); // negativeSampleImage(path);
		String action = request.getParameter("action");
		String wordString = "WORD";
		String imageString = "IMAGE";
		if(wordString.equals(action)) {
			path = wordFacade.getPath();
			path = path.concat(manuscriptName).concat("/");
			wordFacade.updateImagesWords(path, m);   //ho 978 parole e 9161 immagini
		}else {
			if(imageString.equals(action)) {
				path = imageFacade.getPath();
				path = path.concat(manuscriptName).concat("/");
				wordFacade.updateImagesWords(path, m);
				//imageFacade.getListImageProperties(path, m);
			}
		}
		this.manuscriptService.saveManuscript(manuscript);
		return "administration/insertRecap";
	}
	/*-----------------------------------------------------------------------------------------------------------------------------------------*/
	@RequestMapping(value="admin/listJobs") 
	public String jobList(Model model) {

		List<Job> jobs = facadeJob.retriveAlljobs();
		model.addAttribute("jobs", jobs);
		return "administration/listJobs";
	}

	@RequestMapping(value="admin/resultConsole")
	public String resultConsole() {
		return "administration/resultConsole/resultConsole";
	}

	/*-----------------------------CONSOLE DEI RISULTATI------------------------------------------------- */
	@RequestMapping(value="admin/majorityVoting")
	public String majorityVoting(Model model) {
		List<Object> voting = facadeTask.majorityVoting();
		List<MajorityVoting> majority = new ArrayList<>();
		for(Object o : voting) {
			MajorityVoting mj = new MajorityVoting();
			mj.setImageId(((Number)((Object[])o)[0]).intValue());
			mj.setTranscription((String)((Object[])o)[1]);
			mj.setNumberOfYes(((Number)((Object[])o)[2]).intValue());
			majority.add(mj);
		}
		model.addAttribute("majority", majority);

		return "administration/resultConsole/majorityVoting";
	}

	@RequestMapping(value="admin/symbolsAnswer")
	public String symbolsAnswer(Model model) {
		List<Object> answers = facadeTask.symbolAnswers();
		List<SymbolsAnswers> symbolsAnswers = new ArrayList<>();
		for(Object o : answers) {
			SymbolsAnswers sa = new SymbolsAnswers();
			sa.setTranscription((String)((Object[])o)[0]);
			sa.setCompletedTasks(((Number)((Object[])o)[1]).intValue());
			symbolsAnswers.add(sa);
		}
		model.addAttribute("symbolsAnswers", symbolsAnswers);

		return "administration/resultConsole/symbolsAnswer";
	}

	@RequestMapping(value="admin/symbolsMajorityAnswer")
	public String symbolsMajorityAnswer(Model model) {
		List<Object> majorityAnswers = facadeTask.symbolsMajorityAnswers();
		model.addAttribute("pf", majorityAnswers.size());
		return "NewFile";
//		List<MajorityAnswers> majority = new ArrayList<>();
//		for(Object o : majorityAnswers) {
//			MajorityAnswers ma = new MajorityAnswers();
//			ma.setTranscription((String)((Object[])o)[0]);
//			ma.setCount(((Number)((Object[])o)[1]).intValue());
//			majority.add(ma);
//		}
//		model.addAttribute("majority", majority);
//
//		return "administration/resultConsole/symbolsMajorityAnswer";
	}

	@RequestMapping(value="admin/tasksTimes")
	public String tasksTimes(Model model) {
		List<Object> times = facadeTask.taskTimes();
		List<TaskTimes> taskTimes = new ArrayList<>();
		for(Object o : times) {
			TaskTimes ts = new TaskTimes();
			ts.setJobId(((Number) ((Object[])o)[0]).longValue());
			ts.setBatch(((Number) ((Object[])o)[1]).intValue());
			ts.setAvgDate(((String)((Object[])o)[2]).toString());
			ts.setMaxDate(((String)((Object[])o)[3]).toString());
			ts.setMinDate(((String)((Object[])o)[4]).toString());
			taskTimes.add(ts);
		}
		model.addAttribute("taskTimes", taskTimes);
		return "administration/resultConsole/tasksTimes";
	}

	@RequestMapping(value="admin/correctStudentsAnswer")
	public String correctStudentsAnswer(Model model) {
		List<Object> correct = facadeTask.correctStudentsAnswers();
		List<CorrectStudentsAnswer> correctAnswers = new ArrayList<>();
		for(Object o : correct) {
			CorrectStudentsAnswer cs = new CorrectStudentsAnswer();
			cs.setId(((Number)((Object[])o)[0]).intValue());
			cs.setName((String)((Object[])o)[1]);
			cs.setSurname((String)((Object[])o)[2]);
			cs.setCorrectAnswers(((Number)((Object[])o)[3]).intValue());
			correctAnswers.add(cs);
		}
		model.addAttribute("correctAnswers", correctAnswers);
		return "administration/resultConsole/correctStudentsAnswer";
	}

	@RequestMapping(value="admin/voting")
	public String voting(Model model) {
		List<Object> voting = facadeTask.voting();
		List<Voting> listVoting = new ArrayList<>();
		for(Object o : voting) {
			Voting v = new Voting();
			v.setImageId(((Number)((Object[])o)[0]).intValue());
			v.setTranscription((String)((Object[])o)[1]);
			v.setNumbersOfYes(((Number)((Object[])o)[2]).intValue());
			listVoting.add(v);
		}

		model.addAttribute("listVoting", listVoting);
		return "administration/resultConsole/voting";
	}

	@RequestMapping(value="admin/studentsProductivity")
	public String studentsProductivity(Model model) throws IllegalArgumentException, IllegalAccessException {
		List<Object> tasks = facadeTask.studentsProductivity();
		List<Object> tasks2 = facadeTask.studentsProductivity2();
		tasks.addAll(tasks2);
		List<StudentsProductivity> produttivita = new ArrayList<>();
		for(Object o : tasks) {
			StudentsProductivity ps = new StudentsProductivity();
			ps.setId(((Number) ((Object[])o)[0]).longValue());
			ps.setName((String)((Object[])o)[1]);
			ps.setSurname((String)((Object[])o)[2]);
			ps.setNumeroTask(((Number) ((Object[])o)[3]).intValue());
			produttivita.add(ps);
		}
		model.addAttribute("produttivita", produttivita);

		return "administration/resultConsole/studentsProductivity";
	}

	@RequestMapping(value="admin/toChangeAdminPassword")
	public String toChangeAdminPassword(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Administrator a = this.adminFacade.findAdmin(username);
		a.setPassword("");
		model.addAttribute("administrator", a);

		return "administration/changeAdminPassword";
	}

	@RequestMapping(value="admin/changeAdminPassword", method = RequestMethod.POST)
	public String changeAdminPassword(@ModelAttribute Administrator administrator,Model model) {
		if(administrator.getPassword().equals("") || administrator.getPassword()==null){
			model.addAttribute("errPassword", "*Campo Obbligatorio");
			model.addAttribute("administrator", administrator);
			return "administration/changeAdminPassword";
		}
		
		/*PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordEncode = passwordEncoder.encode(administrator.getPassword());
		administrator.setPassword(passwordEncode);*/
		administrator.setPassword(administrator.getPassword());
		adminFacade.addAdmin(administrator);
		return "administration/homeAdmin";
	}
}