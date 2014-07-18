package com.blobLumper.repositories.jpa;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaBasedRepository<T,R extends Serializable> extends JpaRepository<T,R> {
	

}
