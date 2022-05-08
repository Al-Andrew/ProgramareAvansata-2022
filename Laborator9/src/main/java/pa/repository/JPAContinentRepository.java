package pa.repository;

import pa.db.JPAManager;
import pa.entity.Continent;

import java.util.List;

public class JPAContinentRepository extends Repository<Continent> {

    @Override
    public List<Continent> getAll() {
        var em = JPAManager.the().createEntityManager();
        var res = em.createNamedQuery("Continent.getAll").getResultList();
        em.close();
        return res;
    }

    @Override
    public Continent findById(long id) {
        var em = JPAManager.the().createEntityManager();
        var res = em.createNamedQuery("Continent.findById")
                .setParameter(1, id).getSingleResult();
        em.close();
        return (Continent)res;
    }

    @Override
    public Continent findByName(String name) {
        var em = JPAManager.the().createEntityManager();
        var res = em.createNamedQuery("Continent.findByName")
                .setParameter(1, name).getSingleResult();
        em.close();
        return (Continent)res;
    }

    @Override
    public boolean create(Continent toCreate) {
        toCreate.setId(0l); //The id is handled by the PLSQL
        var em = JPAManager.the().createEntityManager();
        em.getTransaction().begin();
        em.persist(toCreate);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public boolean update(long id, Continent replacement) {
        var em = JPAManager.the().createEntityManager();
        em.getTransaction().begin();
        var c = em.find(Continent.class, id);
        c.become(replacement);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public boolean delete(Continent toDelete) {
        var em = JPAManager.the().createEntityManager();
        em.getTransaction().begin();
        var c = em.find(Continent.class, toDelete.getId());
        em.remove(c);
        em.getTransaction().commit();
        em.close();
        return true;
    }
}