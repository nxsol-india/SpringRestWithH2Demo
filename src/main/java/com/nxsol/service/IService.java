package com.nxsol.service;

import java.util.List;
import java.util.Optional;

public interface IService<T>{
	
	T save(T entity);
	
	List<T> saveAll(Iterable<T> entities);

	Optional<T> findById(Long id);

	/**
	 * Delete the entity
	 * 
	 * @return TODO
	 * 
	 * @return the Redis id deleted
	 * @param id
	 *            identificator
	 */
	Long delete(Long id);
	
}
