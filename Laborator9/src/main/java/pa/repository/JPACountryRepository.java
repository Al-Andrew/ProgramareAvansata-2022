package pa.repository;

import pa.db.JPAManager;
import pa.entity.Continent;
import pa.entity.Country;

import javax.persistence.NoResultException;
import java.util.List;

public class JPACountryRepository extends Repository<Country>{
    @Override
    public List<Country> getAll() {
        var em = JPAManager.the().createEntityManager();
        var res = em.createNamedQuery("Country.getAll").getResultList();
        em.close();
        return res;
    }

    @Override
    public Country findById(long id) {
        var em = JPAManager.the().createEntityManager();
        var res = em.createNamedQuery("Country.findById")
                .setParameter("id", id).getSingleResult();
        em.close();
        return (Country)res;
    }

    @Override
    public Country findByName(String name) {
        var em = JPAManager.the().createEntityManager();
        Object res = null;
        try {

        res = em.createNamedQuery("Country.findByName")
                .setParameter(1, name).getSingleResult();
        } catch (NoResultException ex) {
            res = null;
        }
        em.close();
        return (Country)res;
    }

    @Override
    public boolean create(Country toCreate) {
        toCreate.setId(0l); //The id is handled by the PLSQL
        var em = JPAManager.the().createEntityManager();
        em.getTransaction().begin();
        em.persist(toCreate);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public boolean update(long id, Country replacement) {
        var em = JPAManager.the().createEntityManager();
        em.getTransaction().begin();
        var c = em.find(Country.class, id);
        c.become(replacement);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public boolean delete(Country toDelete) {
        var em = JPAManager.the().createEntityManager();
        em.getTransaction().begin();
        var c = em.find(Country.class, toDelete.getId());
        em.remove(c);
        em.getTransaction().commit();
        em.close();
        return true;
    }
}
