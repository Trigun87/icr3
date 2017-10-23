package it.uniroma3.icr.insertImageInDb.utils;

import org.springframework.stereotype.Service;

@Service
public class GetSamplePath extends GetManuscriptPath{

	@Override
	public String getPath() {
		String path = this.getServletContext().getInitParameter("pathSample");
    	return path;
	}
    

	


}
