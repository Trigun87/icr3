package it.uniroma3.icr.insertImageInDb.utils;

import org.springframework.stereotype.Service;
@Service
public class GetImagePath extends GetManuscriptPath{
	@Override
	public String getPath() {
		String path = this.getServletContext().getInitParameter("pathImage");
    	return path;
	}


}
