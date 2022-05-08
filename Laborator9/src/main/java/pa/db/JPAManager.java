package pa.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAManager {
    private EntityManagerFactory innerEntityManagerFactory;
    private static JPAManager inner;

    private JPAManager() {
        this.innerEntityManagerFactory = Persistence.createEntityManagerFactory("LAB9");
    }

    public EntityManager createEntityManager() {
        return the().innerEntityManagerFactory.createEntityManager();
    }

    public static JPAManager the() {
        if(inner == null) {
            inner = new JPAManager();
        }

        return inner;
    }
}
