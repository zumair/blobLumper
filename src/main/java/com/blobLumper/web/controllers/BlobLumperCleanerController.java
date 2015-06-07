package com.blobLumper.web.controllers;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.blobLumper.relational.entities.Blob;
import com.blobLumper.relational.entities.BlobBasePath;
import com.blobLumper.repositories.jpa.BlobBasePathRepository;
import com.blobLumper.repositories.jpa.BlobRepository;

@Table(name = "BlobObject")
@Controller
public class BlobLumperCleanerController {

	
	
	@Autowired
	private BlobBasePathRepository blobBasePathRepository;
	@Autowired
	private BlobRepository blobRepository;
	


	public void cleanTable() {
		// find all Bob entries in Blob object
		List<Blob> blobs = findAllBlobEntries();
		// Traverse those entries
		for (Blob blob : blobs) {
			// check file exist against an entry

			if (!fileExistAgainstBlobEntry(blob)) {
				// if file doesn't exist delete that entry (blobobject via
				// BlobRepository)

				deletBlobEntry(blob);

			}
		}

	}

	private List<Blob> findAllBlobEntries() {
		return blobRepository.findAll();

	}

	private boolean fileExistAgainstBlobEntry(Blob blob) {

		final File file = new File(blob.getFullPath());
		return file.exists();

	}

	private void deletBlobEntry(Blob blob) {
		blobRepository.delete(blob);
	}

	public void cleanFiles() {

		// 1 Find all base paths
		// 2 Traverse base path
		// 3 Against each base path find base path folder{file object}
		// 4 in that folder find all of the folders names {list of strings from
		// file object}
		// 5 now we have got all of folder name which should represent BlobID in
		// blob object table
		// 6 find blob record against folder name which will be find by blob ID
		// 7 if we could not find record then delete a folder

		
		List<BlobBasePath> blobBasePaths = blobBasePathRepository.findAll();

		for (BlobBasePath blobBasePath : blobBasePaths) 
		{
			String basepathfolderPath =blobBasePath.getBasePath();
			File   basepathfolder = new File(basepathfolderPath);
			
			String[] folderNames = basepathfolder.list(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					return new File(dir, name).isDirectory();
				}
			});
			
			for (String blobId : folderNames) {
				
			if(blobId!=blobBasePath.getId().toString())
				
			{
				String[]entries = blobBasePaths.add				
				for(String s: entries){
				    File currentFile = new File(index.getPath(),s);
				    currentFile.delete();
				}
			}
			
			}    
		}
	}

	// find all entries in blob base path

	
	
	

	private void deleteFile(BlobBasePath file) {
		blobBasePathRepository.delete(file);

	}

}
