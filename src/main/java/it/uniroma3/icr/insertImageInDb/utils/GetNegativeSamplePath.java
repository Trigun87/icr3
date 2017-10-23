package it.uniroma3.icr.insertImageInDb.utils;

import org.springframework.stereotype.Service;

@Service
public class GetNegativeSamplePath extends GetManuscriptPath{

	@Override
	public String getPath() {
		String path = this.getServletContext().getInitParameter("pathNegativeSample");
    	return path;
	}

}
