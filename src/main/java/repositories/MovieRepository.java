package repositories;

import domain.entities.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class MovieRepository {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistence");
    private static final EntityManager entityManager = emf.createEntityManager();


    public static List<Movie> findAllMovie(){
        List<Movie> allMovies = entityManager.createQuery("select m from movies m", Movie.class)
                .getResultList();

        return allMovies;
    }
}
