package it.uniroma3.icr.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Sample;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.model.TaskWrapper;
import it.uniroma3.icr.service.editor.ImageEditor;
import it.uniroma3.icr.service.editor.TaskEditor;
import it.uniroma3.icr.service.impl.ImageFacade;
import it.uniroma3.icr.service.impl.JobFacade;
import it.uniroma3.icr.service.impl.NegativeSampleService;
import it.uniroma3.icr.service.impl.ResultFacade;
import it.uniroma3.icr.service.impl.SampleService;
import it.uniroma3.icr.service.impl.StudentFacade;
import it.uniroma3.icr.service.impl.StudentFacadeSocial;
import it.uniroma3.icr.service.impl.SymbolFacade;
import it.uniroma3.icr.service.impl.TaskFacade;

@Controller
public class TaskController {
	private @Autowired ImageEditor imageEditor;
	private @Autowired TaskEditor taskEditor;
	@Autowired
	public SymbolFacade symbolFacade;
	@Autowired
	public SampleService sampleService;
	@Autowired
	public NegativeSampleService negativeSampleService;
	@Autowired
	public JobFacade facadeJob;
	@Autowired
	public ImageFacade imageFacade;
	@Autowired
	public TaskFacade taskFacade;
	@Autowired
	public StudentFacade studentFacade;

	@Autowired
	public StudentFacadeSocial studentFacadesocial;

	@Autowired
	ResultFacade resultFacade;

	@Autowired
	private StudentFacade userFacade;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Image.class, this.imageEditor);
		binder.registerCustomEditor(Task.class, this.taskEditor);
	}

	public @ModelAttribute("taskResults") TaskWrapper setupWrapper() {
		return new TaskWrapper();
	}

	/*
	 * @RequestMapping(value = "user/newTask", method = RequestMethod.GET) public
	 * String taskChoose(@ModelAttribute Task task, @ModelAttribute Job
	 * job, @ModelAttribute Result result,
	 * 
	 * @ModelAttribute("taskResults") TaskWrapper taskResults, Model model,
	 * HttpServletRequest request) { Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication(); String s =
	 * auth.getName(); Student student = studentFacade.findUser(s);
	 * model.addAttribute("student", student); task =
	 * taskFacade.assignTask(student); if (task != null) {
	 * 
	 * List<Sample> positiveSamples =
	 * sampleService.findAllSamplesBySymbolId(task.getJob().getSymbol().getId());
	 * List<Sample> negativeSamples = negativeSampleService
	 * .findAllNegativeSamplesBySymbolId(task.getJob().getSymbol().getId());
	 * 
	 * List<Result> listResults = resultFacade.findTaskResult(task); String url =
	 * "users/newTaskWord";
	 * 
	 * taskResults.setResultList(listResults); for (Result r :
	 * taskResults.getResultList()) {
	 * r.getImage().setPath(r.getImage().getPath().replace(File.separatorChar,
	 * '/')); } model.addAttribute("student", student);
	 * 
	 * model.addAttribute("positiveSamples", positiveSamples);
	 * model.addAttribute("negativeSamples", negativeSamples);
	 * 
	 * model.addAttribute("task", task); model.addAttribute("taskResults",
	 * taskResults); return url; }
	 * 
	 * return "users/goodBye"; }
	 */

	@RequestMapping(value = "user/newTask", method = RequestMethod.GET)
	public String taskChoose(@ModelAttribute Task task, @ModelAttribute Job job, @ModelAttribute Result result,
			@ModelAttribute("taskResults") TaskWrapper taskResults, Model model, HttpServletRequest request,
			@RequestParam(name = "social", required = false) String social) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String s = auth.getName();
		model.addAttribute("social", social);
		Student student;
		if (social == null || social.isEmpty())
			student = studentFacade.findUser(s);
		else
			student = studentFacadesocial.findUser(s);
		model.addAttribute("student", student);
		task = taskFacade.assignTask(student);
		if (task != null) {
			List<Sample> positiveSamples = sampleService.findAllSamplesBySymbolId(task.getJob().getSymbol().getId());
			List<Sample> negativeSamples = negativeSampleService
					.findAllNegativeSamplesBySymbolId(task.getJob().getSymbol().getId());

			List<Result> listResults = resultFacade.findTaskResult(task);
			String url = "users/newTaskImage";
			/*
			 * if(task.getJob().getWords()!=null){ ComparatoreResultPerWordeX c = new
			 * ComparatoreResultPerWordeX(); listResults.sort(c); url =
			 * "users/newTaskWordSocial";
			 * 
			 * }else{ Collections.shuffle(listResults); url = "users/newTaskImage"; }
			 */
			taskResults.setResultList(listResults);
			for (Result r : taskResults.getResultList()) {
				r.getImage().setPath(r.getImage().getPath().replace(File.separatorChar, '/'));
			}
			
			String hint = "[]";
			if (taskResults.getResultList().size()>0)
				hint = taskFacade.findHintByTask(taskResults.getResultList().get(0).getTask());
			else 
				System.err.println("result vuoto - studente: " + student.getId());

			model.addAttribute("student", student);

			model.addAttribute("positiveSamples", positiveSamples);
			model.addAttribute("negativeSamples", negativeSamples);

			model.addAttribute("task", task);
			model.addAttribute("taskResults", taskResults);
			model.addAttribute("hint", hint);
			return url;
		}

		return "users/goodBye";
	}
	/*
	 * @RequestMapping(value = "user/secondConsoleWord", method =
	 * RequestMethod.POST) public String
	 * taskRecapWord(@ModelAttribute("taskResults") TaskWrapper taskResults, Model
	 * model, HttpServletRequest request, HttpServletResponse response) throws
	 * IOException { Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication(); String s =
	 * auth.getName(); Student student = studentFacade.findUser(s);
	 * model.addAttribute("student", student);
	 * 
	 * String action = request.getParameter("action"); String targetUrl = "";
	 * 
	 * String conferma1 = "Conferma e vai al prossimo task"; String conferma2 =
	 * "Conferma e torna alla pagina dello studente";
	 * 
	 * if (conferma1.equals(action)) { for (Result result :
	 * taskResults.getResultList()) { Task task = result.getTask();
	 * taskFacade.updateEndDate(task); if (result.getAnswer() == null)
	 * result.setAnswer("[]"); } resultFacade.updateListResult(taskResults);
	 * response.sendRedirect("newTask"); targetUrl = "users/newTaskWord"; } else {
	 * if (conferma2.equals(action)) { for (Result result :
	 * taskResults.getResultList()) { Task task = result.getTask();
	 * taskFacade.updateEndDate(task); if (result.getAnswer() == null)
	 * result.setAnswer("[]"); } resultFacade.updateListResult(taskResults); }
	 * targetUrl = "users/homeStudent"; }
	 * 
	 * return targetUrl;
	 * 
	 * }
	 */

	@RequestMapping(value = "user/secondConsoleWord", method = RequestMethod.POST)
	public String taskRecapWord(@ModelAttribute("taskResults") TaskWrapper taskResults, Model model,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "social", required = false) String social) throws IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String s = auth.getName();
		model.addAttribute("social", social);
		Student student;
		if (social == null || social.isEmpty())
			student = studentFacade.findUser(s);
		else
			student = studentFacadesocial.findUser(s);
		

		String action = request.getParameter("action");
		String targetUrl = "";

		String conferma1 = "Conferma e vai al prossimo task";
		String conferma2 = "Conferma e torna alla pagina dello studente";

		if (conferma1.equals(action)) {
			int tempTime = 0;
			int tempTask = 0;
			for (Result result : taskResults.getResultList()) {
				Task task = result.getTask();
				taskFacade.updateEndDate(task);
				if (result.getAnswer() == null)
					result.setAnswer("No");
				tempTime = (int)(task.getEndDate().getTime() - task.getStartDate().getTime())/1000;
				if (tempTime > 300) tempTime = 300;
				tempTask++;
			}
			resultFacade.updateListResult(taskResults);
			student.setTempoEffettuato(student.getTempoEffettuato() + tempTime);
			student.setTaskEffettuati(student.getTaskEffettuati() + tempTask);
			userFacade.retrieveUser(student);
			response.sendRedirect("newTask");
			targetUrl = "users/newTaskImage";
		} else {
			if (conferma2.equals(action)) {
				for (Result result : taskResults.getResultList()) {
					Task task = result.getTask();
					taskFacade.updateEndDate(task);
					if (result.getAnswer() == null)
						result.setAnswer("No");
				}
				resultFacade.updateListResult(taskResults);
			}
			targetUrl = "users/homeStudent";
		}
		model.addAttribute("student", student);
		return targetUrl;

	}

	/*
	 * @RequestMapping(value = "user/secondConsole", method = RequestMethod.POST)
	 * public String taskRecap(@ModelAttribute("taskResults") TaskWrapper
	 * taskResults, Model model, HttpServletRequest request, HttpServletResponse
	 * response) throws IOException {
	 * 
	 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	 * String s = auth.getName(); Student student = studentFacade.findUser(s);
	 * model.addAttribute("student", student);
	 * 
	 * String action = request.getParameter("action"); String targetUrl = "";
	 * 
	 * String conferma1 = "Conferma e vai al prossimo task"; String conferma2 =
	 * "Conferma e torna alla pagina dello studente";
	 * 
	 * if (conferma1.equals(action)) { for (Result result :
	 * taskResults.getResultList()) { Task task = result.getTask();
	 * taskFacade.updateEndDate(task); if (result.getAnswer() == null)
	 * result.setAnswer("[]"); } resultFacade.updateListResult(taskResults);
	 * response.sendRedirect("newTask");
	 * 
	 * targetUrl = "users/newTask"; } else { if (conferma2.equals(action)) { for
	 * (Result result : taskResults.getResultList()) { Task task = result.getTask();
	 * taskFacade.updateEndDate(task); if (result.getAnswer() == null)
	 * result.setAnswer("[]"); } resultFacade.updateListResult(taskResults); }
	 * targetUrl = "users/homeStudent"; }
	 * 
	 * return targetUrl;
	 * 
	 * }
	 */

	@RequestMapping(value = "user/secondConsole", method = RequestMethod.POST)
	public String taskRecap(@ModelAttribute("taskResults") TaskWrapper taskResults, Model model,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "social", required = false) String social) throws IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String s = auth.getName();
		Student student;
		if (social == null || social.isEmpty())
			student = studentFacade.findUser(s);
		else
			student = studentFacadesocial.findUser(s);
		model.addAttribute("student", student);
		model.addAttribute("social", social);
		String action = request.getParameter("action");
		String targetUrl = "";

		String conferma1 = "Conferma e vai al prossimo task";
		String conferma2 = "Conferma e torna alla pagina dello studente";

		if (conferma1.equals(action)) {
			for (Result result : taskResults.getResultList()) {
				Task task = result.getTask();
				taskFacade.updateEndDate(task);
				if (result.getAnswer() == null)
					result.setAnswer("No");
			}
			resultFacade.updateListResult(taskResults);
			response.sendRedirect("newTask");

			targetUrl = "users/newTask";
		} else {
			if (conferma2.equals(action)) {
				for (Result result : taskResults.getResultList()) {
					Task task = result.getTask();
					taskFacade.updateEndDate(task);
					if (result.getAnswer() == null)
						result.setAnswer("No");
				}
				resultFacade.updateListResult(taskResults);
			}
			targetUrl = "users/homeStudent";
		}

		return targetUrl;

	}

	/*
	 * @RequestMapping(value = "user/studentTasks") public String studentTasks(Model
	 * model) { Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication(); Student s =
	 * studentFacade.findUser(auth.getName()); List<Task> studentTasks =
	 * taskFacade.findTaskByStudent(s.getId()); Collections.sort(studentTasks, new
	 * ComparatorePerData()); model.addAttribute("studentTasks", studentTasks);
	 * model.addAttribute("s", s); return "users/studentTasks"; }
	 */

	@RequestMapping(value = "user/studentTasks")
	public String studentTasks(Model model, @RequestParam(name = "social", required = false) String social) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Student s;
		if (social == null || social.isEmpty()) {
			s = studentFacade.findUser(auth.getName());
			//studentTasks = taskFacade.findTaskByStudent(s.getId());
		} else {
			s = studentFacadesocial.findUser(auth.getName());
			//studentTasks = taskFacade.findTaskByStudentSocial(s.getId());
		}
		/*List<Task> studentTasks;
		

		Collections.sort(studentTasks, new ComparatorePerData());
		model.addAttribute("studentTasks", studentTasks);*/
		model.addAttribute("s", s);
		model.addAttribute("social", social);
		return "users/studentTasks";
	}

}