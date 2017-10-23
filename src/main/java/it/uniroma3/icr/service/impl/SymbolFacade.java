package it.uniroma3.icr.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.SymbolDao;
import it.uniroma3.icr.insertImageInDb.utils.GetSamplePath;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Symbol;

@Service
public class SymbolFacade {

	@Autowired
	private SymbolDao symbolDao;
	@Autowired
	private GetSamplePath getSamplePath;
	
	public void insertSymbolInDb(String p, Manuscript manuscript) throws FileNotFoundException, IOException {
		File[] files = new File(p).listFiles();
		for(int i=0;i<files.length;i++) {
			String typeSymbol = files[i].getName();
			File[] transcriptionsSymbol = files[i].listFiles();
			for(int j=0;j<transcriptionsSymbol.length;j++) {
				String transcriptionSymbol = transcriptionsSymbol[j].getName();
				File[] images = transcriptionsSymbol[j].listFiles();
				File image = images[0];
				String nameComplete = image.getName();
				String name = FilenameUtils.getBaseName(nameComplete);
				String parts[] = name.split("_");
				int width = Integer.valueOf(parts[0]);
				BufferedInputStream in = null;
				try {
					String transcription = transcriptionSymbol;
					String type = typeSymbol;
					
					Symbol symbol = new Symbol(transcription,type,manuscript,width);
					manuscript.addSymbol(symbol);
					this.insertSymbol(symbol);
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
	
	public Symbol retrieveSymbol(long id) {
		return this.symbolDao.findOne(id);
	}
		
	public List<Symbol> retrieveAllSymbols() {
		return this.symbolDao.findAll();
	}
	
	public void insertSymbol(Symbol symbol) {
		symbolDao.save(symbol);
	}

	public List<Symbol> findSymbolByManuscriptName(String manuscript) {
		return this.symbolDao.findByManuscriptName(manuscript);
	}
	public String getPath() {
		return this.getSamplePath.getPath();
	}
    public List<Manuscript> getManuscript() throws FileNotFoundException, IOException {
    	return this.getSamplePath.getManuscript();
    }
	
}
