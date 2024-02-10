package web.rest.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import web.rest.model.Shot;
import web.rest.model.User;

import java.util.List;

@Stateless
public class ShotRepository {
    @PersistenceContext
    private EntityManager db;

    public Shot save(Shot shot) {
        db.persist(shot);
        return shot;
    }
    public List<Shot> findByUser(User user) {
        return db.createQuery("from Shot s where s.user = :user", Shot.class).setParameter("user", user).getResultList();
    }
}
