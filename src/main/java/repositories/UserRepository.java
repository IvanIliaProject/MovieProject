package repositories;

import domain.entities.User;
import domain.entities.UserType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserRepository{

    private static final  EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistence");
    private static final EntityManager entityManager = emf.createEntityManager();

    public static List<User> findAll() {

        List<User> users = entityManager.createQuery("select u from users u", User.class)
                .getResultList();

        return users;
    }

    public static User findUser(String email, String password) {
        User user = entityManager.createQuery("select u from users u where u.email = :email and u.password = :password",User.class )
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();

        if (user == null){
            return null;
        }
        return user;
    }

}
