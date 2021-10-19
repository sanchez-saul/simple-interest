package com.aplazo.credit.service;

import java.util.List;

public interface ICRUD<T, ID> {

	T create(T t) throws Exception;
	T update(T t) throws Exception;
	List<T> get() throws Exception;
	T getById(ID id) throws Exception;
	void delete(ID id) throws Exception;
}
