package it.uniroma3.icr.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.WordDao;
import it.uniroma3.icr.insertImageInDb.utils.GetImagePath;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Word;

@Service
public class WordFacade {
	@Autowired
	private GetImagePath getImagePath;
	@Autowired
	private WordDao wordDao;

	public void updateImagesWords(String p, Manuscript manuscript) throws IOException {
		File file = new File(p);
		File[] subFiles = file.listFiles();
		for (int i = 0; i < subFiles.length; i++) {
			String page = subFiles[i].getName();
			// File[] rows = subFiles[i].listFiles();
			String row = subFiles[i].getName();

			File[] images = subFiles[i].listFiles();

			/*
			 * for(int m=0; m<rows.length; m++) { String row = rows[m].getName(); File[]
			 * words = rows[m].listFiles(); for(int y=0; y<words.length; y++){ String
			 * wordName = words[y].getName(); Word word = new Word(); File[] files =
			 * words[y].listFiles(); File[] images = files[1].listFiles(); //prendo solo la
			 * cartella cut_point_view
			 */
			for (int z = 0; z < images.length; z++) {
				Word word = new Word();
				String wordName = subFiles[i].getName();
				String image = FilenameUtils.getBaseName(images[z].getName());
				String path = images[z].getPath().replace("\\", "/");
				path = path.substring(path.indexOf("main/resources/static") + 22, path.length());
				Image img = new Image();
				this.updateImage(img, image, manuscript, page, row, word, path);
				if (word.getHeight() == null)
					word.setHeight(img.getHeight());
				word.addImage(img);
				word.findWidth();
				this.updateWord(word, wordName, manuscript, page, row);
			}

		}
		// }
		// }
	}

	// set parametri di una image
	public Image updateImage(Image img, String name, Manuscript manuscript, String page, String row, Word word,
			String path) {
		// divido il path delle cartelle e lo rigiro per ottenere sempre per prime le
		// info importanti
		img.setManuscript(manuscript);
		img.setPage(page);
		img.setRow(row);
		img.setWord(word);
		img.setPath(path);
		/*
		 * // divido il nome della parola String[] listType = name.split("\\_");
		 * img.setX(Integer.valueOf(listType[0]));
		 * img.setY(Integer.valueOf(listType[1]));
		 * img.setWidth(Integer.valueOf(listType[2]));
		 * img.setHeight(Integer.valueOf(listType[3]));
		 */
		manuscript.addImage(img);
		return img;
	}

	// set word
	public void updateWord(Word word, String name, Manuscript manuscript, String page, String row) {
		Pattern pattern = Pattern.compile("[0-9]*");

		Matcher matcher = pattern.matcher(name);
		int count = 0;
		while (matcher.find()) {
			if (count == 0) {
				word.setX(Integer.valueOf(matcher.group()));

			}
			count++;
		}

		// word.setX(Integer.valueOf(wordAttributes[0]));
		// word.setY(Integer.valueOf(wordAttributes[1]));
		word.setManuscript(manuscript);
		word.setPage(page);
		word.setRow(row);
		word.setName(name);
		manuscript.addWord(word);
		// this.wordDao.save(word);
	}

	public void addWord(Word w) {
		wordDao.save(w);
	}

	public Word retrieveWord(long id) {
		return this.wordDao.findOne(id);
	}

	public List<Word> retrieveAllWords() {
		return this.wordDao.findAll();
	}

	public List<Word> getWordsForManuscriptName(String manuscript, int limit) {
		return this.wordDao.findWordForManuscriptName(manuscript, limit);
	}

	public List<Manuscript> getManuscript() throws FileNotFoundException, IOException {
		return this.getImagePath.getManuscript();
	}

	public String getPath() {
		return this.getImagePath.getPath();
	}

	public List<String> findAllPages() {
		return this.wordDao.findAllPages();
	}

	public Object[] countWods() {
		return this.wordDao.countWord();
	}

}
