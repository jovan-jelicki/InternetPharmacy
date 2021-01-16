package app.service;

import java.util.Collection;
import java.util.Optional;

public interface CRUDService<T> {
    T save(T entity);
    Collection<T> read();
    Optional<T> read(Long id);
    void delete(Long id);
    boolean existsById(Long id);
}
