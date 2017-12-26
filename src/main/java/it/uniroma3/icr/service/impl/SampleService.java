package it.uniroma3.icr.service.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.SampleDao;
import it.uniroma3.icr.dao.SymbolDao;
import it.uniroma3.icr.insertImageInDb.utils.GetSamplePath;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Sample;
import it.uniroma3.icr.model.Symbol;

@Service
public class SampleService {
	@Autowired
	public SampleDao sampleDao;
	@Autowired
	public SymbolDao symbolDao;
	@Autowired
	private GetSamplePath getSamplePath ;
	
	public void getSampleImage(String p, Manuscript manuscript) throws FileNotFoundException, IOException {
		File[] files = new File(p).listFiles();

		for(int i=0;i<files.length;i++) {
			String typeSymbol = files[i].getName();
			File[] transcriptionsSymbol = files[i].listFiles();
			for(int j=0;j<transcriptionsSymbol.length;j++) {
				String transcriptionSymbol = transcriptionsSymbol[j].getName();
				File[] images = transcriptionsSymbol[j].listFiles();
				for(int m=0;m<images.length;m++) {
					String nameComplete = images[m].getName();
					String pathFile = images[m].getPath().replace("\\", "/");
					String name = FilenameUtils.getBaseName(nameComplete);
					String parts[] = name.split("_");
					int width = Integer.valueOf(parts[0]);
					int x = Integer.valueOf(parts[1]);
					int y = Integer.valueOf(parts[2]);
					BufferedInputStream in = null;

					try {
						BufferedImage f = ImageIO.read(images[m]);

						Symbol s = this.symbolDao.findByTranscriptionAndManuscriptName(transcriptionSymbol,manuscript.getName());

						int height = f.getHeight();
						int xImg = x;
						int yImg = y;
//						String path = pathFile.substring(pathFile.indexOf("main/resources/static")+22, pathFile.length());
						String path = pathFile.substring(pathFile.indexOf("classes/static") + 15, pathFile.length());
						String type = typeSymbol;

						Sample sample = new Sample(width,height,xImg,yImg,manuscript,
								type,path);

						sample.setSymbol(s);
						manuscript.addSample(sample);
//						this.sampleDao.save(sample);


					}
					finally {
						if (in != null) {
							try {

								in.close();
							}
							catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	public List<Sample> findAllSamplesBySymbolId(long id){
		return sampleDao.findAllSamplesBySymbolId(id);
	}
	public String getPath() {
		return this.getSamplePath.getPath();
	}
    public List<Manuscript> getManuscript() throws FileNotFoundException, IOException {
    	return this.getSamplePath.getManuscript();
    }
}
