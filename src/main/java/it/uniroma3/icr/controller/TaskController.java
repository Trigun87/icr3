package it.uniroma3.icr.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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

		task = taskFacade.assignTask(student);    // blocca task

		if ((task != null) && (task.getStudent() !=null)) {
			task.setStudent(student);
			
			LOGGER.info("1 - assigned Task " + task.getId() + " to student "+ student.getId() + " (" + task.getStudent().getId() +")");
			List<Sample> positiveSamples = sampleService.findAllSamplesBySymbolId(task.getJob().getSymbol().getId());
			List<Sample> negativeSamples = negativeSampleService.findAllNegativeSamplesBySymbolId(task.getJob().getSymbol().getId());

//			List<Result> listResults = resultFacade.findTaskResult(task);
			List<Result> listResults = taskFacade.findTaskResult(task, student);
			
			taskResults.setResultList(listResults);
			for (Result r : taskResults.getResultList()) {
				LOGGER.info("2 - retrieved task " + r.getTask().getId() +" student " + r.getTask().getStudent().getId() + " (for " + student.getId() + ")");
				r.getImage().setPath(r.getImage().getPath().replace(File.separatorChar, '/'));
			}

			String hint = taskFacade.findHintByTask(taskResults.getResultList().get(0).getTask());
					
			LOGGER.info("3 - hint on task "+ task.getId() +" to student "+ student.getId());

			for (Result r : taskResults.getResultList()) {
				LOGGER.info("3.1 - hint on task "+ task.getId() + "(" + r.getTask().getId() +") to student " + student.getId() + "(" + r.getTask().getStudent().getId() +")" + " result "+r.getId());
			}

			model.addAttribute("student", student);
			model.addAttribute("positiveSamples", positiveSamples);
			model.addAttribute("negativeSamples", negativeSamples);
			model.addAttribute("task", task);
			model.addAttribute("taskResults", taskResults);
			model.addAttribute("hint", hint);
			
			LOGGER.info("4 - end taskChoose task " + 
					task.getId() + "(task results " + 
					taskResults.getResultList().get(0).getTask().getId() + 
					" size " + taskResults.getResultList().size() + 
					") by student " + student.getId() + " (" + 
					taskResults.getResultList().get(0).getTask().getStudent().getId() + ")");				

			return "users/newTaskImage";
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
		String username = auth.getName();
		model.addAttribute("social", social);
		Student student;

		if (social == null || social.isEmpty())
			student = studentFacade.findUser(username);
		else
			student = studentFacadesocial.findUser(username);

		LOGGER.info("5 - Auth name " + username + ", student: " + student.getId());
		String action = request.getParameter("action");
		String targetUrl = "";

		String conferma1 = "Conferma e vai al prossimo task";
		boolean differentId = false;
		
		if (conferma1.equals(action)) {
			for (Result result : taskResults.getResultList()) {
				LOGGER.info("5.0 - task: " + result.getTask().getId() + " result " + result.getId() + " student.getId() " + student.getId() +" - result.getTask().getStudent().getId(): " + result.getTask().getStudent().getId());
				if(!student.getId().equals(result.getTask().getStudent().getId())) {
					LOGGER.info("5.1 - task: " + result.getTask().getId() + " result " + result.getId() + " student.getId() " + student.getId() +" - result.getTask().getStudent().getId(): " + result.getTask().getStudent().getId());
					Long id = taskFacade.findStudentIdOnTask(result.getTask());
					if (student.getId()!=id) { // non e' il mio task
						LOGGER.info("5.1.1 - task: " + result.getTask().getId() + " student.getId() " + student.getId() +" - result.getTask().getStudent().getId(): " + result.getTask().getStudent().getId());
						differentId = true;
					}
				}
			}			
			if (!differentId) {
				int tempTime = 0;
				int tempTask = 0;
				for (Result result : taskResults.getResultList()) {
					Task task = result.getTask();
					taskFacade.updateEndDate(task);
					if (result.getAnswer() == null)
						result.setAnswer("No");
					tempTime = (int)(task.getEndDate().getTime() - task.getStartDate().getTime())/1000;
					if (tempTime > 300) 
						tempTime = 300;
					tempTask++;
					LOGGER.info("6 - task " + task.getId() +" accomplished by student " + student.getId() + " - result " + result.getId());
				}
				
				resultFacade.updateListResult(taskResults);
				
				for (Result result : taskResults.getResultList()) {
					LOGGER.info("7 - AFTER update: task " + result.getTask().getId()+" accomplished by student " + student.getId() + " - result " + result.getId());
				}
				student.setTempoEffettuato(student.getTempoEffettuato() + tempTime);
				student.setTaskEffettuati(student.getTaskEffettuati() + tempTask);
				for (Result result : taskResults.getResultList()) {
					LOGGER.info("8 - before save: task "+ result.getTask().getId() +" accomplished by student " + student.getId() + " - result " + result.getId());
				}
				taskFacade.updateStudent(student);
				//userFacade.saveUser(student);  

				for (Result result : taskResults.getResultList()) {
					LOGGER.info("9 - after save: task "+ result.getTask().getId() +" accomplished by student " + student.getId() + " - result " + result.getId());
				}
				model.addAttribute("student", student);
			}

			
//			model.addAttribute("taskResults", taskResults);
			
			response.sendRedirect("newTask");
			targetUrl = "users/newTaskImage";
		} 
//		else {
//			if (conferma2.equals(action)) {
//				for (Result result : taskResults.getResultList()) {
//					Task task = result.getTask();
//					taskFacade.updateEndDate(task);
//					if (result.getAnswer() == null)
//						result.setAnswer("No");
//				}
//				resultFacade.updateListResult(taskResults);
//			}
//			targetUrl = "users/homeStudent";
//		}
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

	@RequestMapping(value = "user/secondConsole_", method = RequestMethod.POST)
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