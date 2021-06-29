package com.eu.microservicios.commons.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntityService<T> {
	
	public Page<T> find(Pageable pageable);
	
	public Page<T> find(Pageable pageable, T entity);
	
	public Page<T> findAll(Pageable pageable);
	
	public Iterable<T> findAll();
	
	public T save(T entity);

	public Optional<T> findById(Long id);
	
	public void deleteById(Long id);

}
