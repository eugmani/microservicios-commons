package com.eu.microservicios.commons.model.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EntityDAO<T> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T> {
	

}
