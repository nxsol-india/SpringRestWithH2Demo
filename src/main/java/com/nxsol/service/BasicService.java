package com.nxsol.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public abstract class BasicService<T, R extends JpaRepository<T, Long> & JpaSpecificationExecutor<T>> implements IFinder<T>, IService<T> {

	@Autowired
	protected R repository;

	@Override
	public T save(T entity) {
		return repository.save(entity);
	}

	@Override
	public  List<T> saveAll(Iterable<T> entities){
		return repository.saveAll(entities);
	}

	public Optional<T> findById(Long id){
		return repository.findById(id);
	}

	@Override
	public Page<T> findAll(Pageable pageable){
		return repository.findAll(pageable);
	}

	@Override
	public Page<T> findAll(T entity, Pageable pageable) {
		if (entity == null)
			return repository.findAll(pageable);
		return repository.findAll(Example.of(entity), pageable);
	}

	@Override
	public Page<T> findAll(Specification<T> specification, Pageable pageable) {
		return repository.findAll(specification, pageable); 
	}


	@Override
	public List<T> findAll(Specification<T> specification) {
		return repository.findAll(specification); 
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public Long delete(Long id) {
		repository.deleteById(id);
		return id;
	}
}
