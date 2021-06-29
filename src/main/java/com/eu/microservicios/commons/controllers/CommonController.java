package com.eu.microservicios.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eu.microservicios.commons.services.EntityService;

public class CommonController<T, S extends EntityService<T>> {
	
	@Autowired
	protected S service;	
	
	@GetMapping
	public ResponseEntity<Page<T>> find(Pageable pageable) {
		
		Page<T> resultList = this.service.find(pageable);
		
		return ResponseEntity.ok().body(resultList);
	}
	
	@PostMapping("/findBy")
	public ResponseEntity<Page<T>> findBy(@RequestBody T entity, Pageable pageable) {
		
		Page<T> resultList = this.service.find(pageable, entity);
		
		return ResponseEntity.ok().body(resultList);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id) {
		Optional<T> opt = this.service.findById(id);
		
		if(opt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(opt.get());
	}
	
	@PostMapping
	public ResponseEntity<?> crear(@Valid @RequestBody T entity, BindingResult result) {
		
		if(result.hasErrors()) {
			return getErrors(result);
		}
		
		ResponseEntity<?> re = validarCrear(entity);
		
		if(re != null)
			return re;
		
		T dbEntity = service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(dbEntity);
	}
	
	@PutMapping
	public ResponseEntity<?> editar(@Valid @RequestBody T entity, BindingResult result) {
		
		if(result.hasErrors()) {
			return getErrors(result);
		}
		
		ResponseEntity<?> re = validarEditar(entity);
		
		if(re != null)
			return re;
		
		T dbEntity = this.service.save(entity);
		
		if(dbEntity != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(dbEntity);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		Optional<T> opt = this.service.findById(id);
		
		if(opt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		ResponseEntity<?> re = validarEliminar(opt.get());
		
		if(re != null)
			return re;
				
		this.service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	protected ResponseEntity<?> getErrors(BindingResult result) {
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()); 
		});
		return ResponseEntity.badRequest().body(errores);
	}
	
	
	protected ResponseEntity<?> validarEditar(T entity) {
		return null;
	}
	
	protected ResponseEntity<?> validarCrear(T entity) {
		return null;
	}
	
	protected ResponseEntity<?> validarEliminar(T entity) {
		return null;
	}
	
}
