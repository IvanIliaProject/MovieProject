package controllers;

import domain.entities.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.*;
public class LoginController {



    public void manager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Persistence");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
      /*  User user = new User();
        user.setName("Vanko");
        entityManager.persist(user);
        entityManager.getTransaction().commit();
*/
    }
}
