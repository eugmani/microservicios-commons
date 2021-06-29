package com.eu.microservicios.commons.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.eu.microservicios.commons.model.dao.EntityDAO;
import com.eu.microservicios.commons.services.EntityService;

public class EntityServiceImpl<T, D extends EntityDAO<T>> implements EntityService<T> {

	@Autowired
	protected D dao;
	
	@Override
	@Transactional(readOnly = true)
	public Page<T> find(Pageable pageable) {
		return this.dao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<T> find(Pageable pageable, T entity) {
		return this.dao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<T> findAll(Pageable pageable) {
		return this.dao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<T> findAll() {
		return this.dao.findAll(Sort.by("nombre"));
	}
	
	@Override
	@Transactional
	public T save(T entity) {
		return this.dao.save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<T> findById(Long id) {
		return this.dao.findById(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.dao.deleteById(id);		
	}
	
}
