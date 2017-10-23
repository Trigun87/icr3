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

import it.uniroma3.icr.dao.ImageDao;
import it.uniroma3.icr.insertImageInDb.utils.GetImagePath;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Word;

@Service
public class ImageFacade {
	@Autowired
	private GetImagePath getImagePath ;

	@Autowired
	private ImageDao imageDao;

	public void getListImageProperties(String p, Manuscript manuscript) throws FileNotFoundException, IOException {


		File file = new File(p);
		File[] subFiles = file.listFiles();
		for(int i=0; i<subFiles.length; i++) {
			String page = subFiles[i].getName();
			File[] rows = subFiles[i].listFiles();
			for(int m=0; m<rows.length; m++) {
				String row = rows[m].getName();
				File[] words = rows[m].listFiles();
				for(int y=0; y<words.length; y++){
					File[] files = words[y].listFiles();
					File[] images = files[1].listFiles(); //prendo solo la cartella cut_point_view
					for(int z=0; z<images.length; z++){
						String image = FilenameUtils.getBaseName(images[z].getName());
						Word word= new Word();
						String path = images[z].getPath();
						path = path.substring(path.indexOf("main\\resources\\static")+22, path.length());
						Image img = new Image();
						this.updateImage(img,image,manuscript,page,row,word,path);
						
				}
			}
			}
		}
	}
	
	
	/*
	 	File[] files = new File(p).listFiles();

		for(int i=0;i<files.length;i++) {

			String namePage = files[i].getName();

			File[] types = files[i].listFiles();
			for(int m=0;m<types.length;m++) {
				String typeName = types[m].getName();
				File[] images = types[m].listFiles();
				for(int n=0;n<images.length;n++) {
					String nameComplete = images[n].getName();
					String pathFile = images[n].getPath();

					String name = FilenameUtils.getBaseName(nameComplete);
					String[] parts = name.split("_");

					int width = Integer.valueOf(parts[0]);
					int x = Integer.valueOf(parts[1]);
					int y = Integer.valueOf(parts[2]);
					

					BufferedInputStream in = null;

					try {
						BufferedImage b = ImageIO.read(images[n]);

						int height = b.getHeight();
						int xImg = x;
						int yImg = y;
						String page = namePage;
						String type = typeName;
						String path = pathFile.substring(69, pathFile.length());
						Image img = new Image(width,height,type,manuscript,page,xImg,yImg,path);

//						this.imageDao.save(img);

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
	 */
	
	public Image updateImage(Image img, String name, Manuscript manuscript, String page, String row, Word word, String path){
		//divido il path delle cartelle e lo rigiro per ottenere sempre per prime le info importanti
		img.setManuscript(manuscript);
		img.setPage(page);
		img.setRow(row);
		img.setWord(word);
		img.setPath(path);
		//divido il nome della parola
		String[] listType = name.split("\\_");
		img.setX(Integer.valueOf(listType[0]));
		img.setY(Integer.valueOf(listType[1]));
		img.setWidth(Integer.valueOf(listType[2]));
		img.setHeight(Integer.valueOf(listType[3]));
		manuscript.addImage(img);
		return img;
	}
	
	public void addImage(Image i) {
		imageDao.save(i);
	}

	public Image retrieveImage(long id) {
		return this.imageDao.findOne(id);
	}

	public List<Image> retrieveAllImages() {
		return this.imageDao.findAll();
	}

	public List<Image> getImagesForTypeAndManuscriptName(String type, String manuscript, int limit) {
		return this.imageDao.findImageForTypeAndManuscriptName(type,manuscript, limit);
	}

	public List<Image> findImageForTypeAndWidthAndManuscript(String type, String manuscript, int width, int limit) {
		return this.imageDao.findImageForTypeAndWidthAndManuscript(type, manuscript, width, limit);
	}


	public List<Manuscript> getManuscript() throws FileNotFoundException, IOException {
		return this.getImagePath.getManuscript();
	}
	
	public String getPath() throws FileNotFoundException, IOException {
		return this.getImagePath.getPath();
	}

	public List<String> findAllPages() {
		return this.imageDao.findAllPages();
	}

	public Object[] countImage() {
		return this.countImage();
	}

}


