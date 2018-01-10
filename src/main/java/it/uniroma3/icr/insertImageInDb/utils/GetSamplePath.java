package it.uniroma3.icr.insertImageInDb.utils;

import org.springframework.stereotype.Service;

@Service
public class GetSamplePath extends GetManuscriptPath{

	@Override
	public String getPath() {
		String path = "/opt/apache-tomcat-7.0.76/webapps/icr3-1.0/WEB-INF/classes/static/img/samples/";
//		String path = System.getProperty("user.dir") + this.getServletContext().getInitParameter("pathSample");
		return path;
	}
    

	


}
