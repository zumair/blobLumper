package com.blobLumper.repositories.jpa;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import com.blobLumper.relational.entities.BlobBasePath;

public interface BlobBasePathRepository extends JpaBasedRepository<BlobBasePath, Long> {
	
	@Query("SELECT bp FROM BlobBasePath bp WHERE bp.active = true")
	public Set<BlobBasePath> findAllActiveBasePaths();
	
	@Query("SELECT bp FROM BlobBasePath bp WHERE bp.active = true order by bp.folderCount asc")
	public List<BlobBasePath> findBasePathWithLeastFolderCount();
	
	
}