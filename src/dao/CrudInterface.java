package dao;

import java.util.List;

public interface CrudInterface<T>{

     T save(T entity) throws Exception;

    List<T> findAll() throws Exception;

    T update(T entity) throws Exception;

    T findById(Long id) throws Exception;

    void delete(Long id) throws Exception;



}
