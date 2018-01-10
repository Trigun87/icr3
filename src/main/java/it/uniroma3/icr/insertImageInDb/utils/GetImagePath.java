package it.uniroma3.icr.insertImageInDb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class GetImagePath extends GetManuscriptPath{
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public String getPath() {
		String path = "/opt/apache-tomcat-7.0.76/webapps/icr3-1.0/WEB-INF/classes/static/img/images/";
//		String path = System.getProperty("user.dir") + this.getServletContext().getInitParameter("pathImage");

		LOGGER.debug("PATH: "+path);
		return path;
	}


}
