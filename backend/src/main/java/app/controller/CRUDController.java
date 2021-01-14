package app.controller;

import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Optional;

public interface CRUDController<T> {
    ResponseEntity<T> save(T entity);
    ResponseEntity<T> update(T entity);
    ResponseEntity<Collection<T>> read();
    ResponseEntity<Optional<T>> read(Long id);
    ResponseEntity<Void> delete(Long id);
}
