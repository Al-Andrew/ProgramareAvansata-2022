package pa.repository;

import net.bytebuddy.asm.Advice;
import pa.entity.City;
import pa.entity.Continent;
import pa.entity.Country;

import java.util.List;

public abstract class Repository<E> {
    static public <RepoObject> Repository<RepoObject> make(Class<RepoObject> cls, RepoType type) {
        if (cls.equals(City.class)) {
            switch (type) {
                case JBDC -> {
                    return (Repository<RepoObject>) new JDBCCityRepository();
                }
                case JPA -> {
                    return (Repository<RepoObject>) new JPACityRepository();
                }
            }
            return null;
        } else if (cls.equals(Country.class)) {
            switch (type) {

                case JBDC -> {
                    return (Repository<RepoObject>) new JDBCCountryRepository();
                }
                case JPA -> {
                    return (Repository<RepoObject>) new JPACountryRepository();
                }
            }
        } else if (cls.equals(Continent.class)) {
            switch (type) {

                case JBDC -> {
                    return (Repository<RepoObject>) new JDBCContinentRepository();
                }
                case JPA -> {
                    return (Repository<RepoObject>) new JPAContinentRepository();
                }
            }
        }


        return null;
    }

    abstract public List<E> getAll();

    abstract public E findById(long id);

    abstract public E findByName(String name);

    abstract public boolean create(E toCreate);

    abstract public boolean update(long id, E replacement);

    abstract public boolean delete(E toDelete);
}
