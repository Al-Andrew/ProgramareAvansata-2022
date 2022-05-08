package pa.repository;

import java.util.List;

public abstract class Repository<E> {
    abstract public List<E> getAll();
    abstract public E findById(long id);
    abstract public E findByName(String name);
    abstract public boolean create(E toCreate);
    abstract public boolean update(long id, E replacement);
    abstract public boolean delete(E toDelete);
}
