package pa.repository;

import pa.db.JPAManager;
import pa.entity.City;
import pa.entity.Continent;
import pa.entity.Country;

import java.util.List;

public class JPACityRepository extends Repository<City> {
    @Override
    public List<City> getAll() {
        var em = JPAManager.the().createEntityManager();
        var res = em.createNamedQuery("City.getAll").getResultList();
        em.close();
        return res;
    }

    @Override
    public City findById(long id) {
        var em = JPAManager.the().createEntityManager();
        var res = em.createNamedQuery("City.findById")
                .setParameter(1, id).getSingleResult();
        em.close();
        return (City)res;
    }

    @Override
    public City findByName(String name) {
        var em = JPAManager.the().createEntityManager();
        var res = em.createNamedQuery("City.findByName")
                .setParameter(1, name).getResultList().get(0);
        em.close();
        return (City)res;
    }

    public List<City> findNamed(String name) {
        var em = JPAManager.the().createEntityManager();
        var res = em.createNamedQuery("City.findByName").setParameter(1, name).getResultList();

        return res;
    }

    @Override
    public boolean create(City toCreate) {
        var em = JPAManager.the().createEntityManager();
        em.getTransaction().begin();
        em.persist(toCreate);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public boolean update(long id, City replacement) {
        var em = JPAManager.the().createEntityManager();
        em.getTransaction().begin();
        var c = em.find(City.class, id);
        c.become(replacement);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public boolean delete(City toDelete) {
        var em = JPAManager.the().createEntityManager();
        em.getTransaction().begin();
        var c = em.find(City.class, toDelete.getId());
        em.remove(c);
        em.getTransaction().commit();
        em.close();
        return true;
    }
}
