package com.aplazo.credit.service.impl;

import java.util.List;

import com.aplazo.credit.service.ICRUD;
import com.aplazo.credit.repo.IGenericRepo;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

	protected abstract IGenericRepo<T, ID> getRepo();
	
	@Override
	public T create(T t) throws Exception {
		return getRepo().save(t);
	}

	@Override
	public T update(T t) throws Exception {
		return getRepo().save(t);
	}

	@Override
	public List<T> get() throws Exception {
		return getRepo().findAll();
	}

	@Override
	public T getById(ID id) throws Exception {
		return getRepo().findById(id).orElse(null);
	}

	@Override
	public void delete(ID id) throws Exception {
		getRepo().deleteById(id);
	}

}
