package api.controller;

import java.util.Collection;

public interface IControllerApi<T> {

    T findById(Long id);
    Collection<T> findAll();
    T save(T t);
    T update(T t);
    void delete(T t);

}
