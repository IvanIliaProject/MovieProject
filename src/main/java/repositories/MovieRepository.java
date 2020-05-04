package repositories;

import domain.entities.Movie;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.util.List;

public class MovieRepository {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistence");
    private static final EntityManager entityManager = emf.createEntityManager();


    public static List<Movie> findAllMovie(){
        List<Movie> allMovies = entityManager.createQuery("select m from movies m", Movie.class)
                .getResultList();

        return allMovies;
    }

    public static Movie findMovieByTitle(String title){
        Movie movie = entityManager.createQuery("select m from movies m where m.name = :title", Movie.class)
                .setParameter("title", title)
                .getSingleResult();

        return movie;
    }

    public static List<Movie> findByDate(Date from, Date to){
        List<Movie> movie =  entityManager.createQuery("select m from movies m where m.date between :from and :to", Movie.class)
                .setParameter("from", from)
                .setParameter("to", to).getResultList();
        return movie;
    }
    public static List<Movie> findByPrice(double priceFrom, double priceTo){
        List<Movie> movieByPrice = entityManager.createQuery("select m from movies m where m.price between" +
                " :priceFrom and :priceTo", Movie.class)
                .setParameter("priceFrom", priceFrom)
                .setParameter("priceTo", priceTo)
                .getResultList();

        return movieByPrice;
    }
    public static List<Movie> findByGenre(String genre){
        List<Movie> movieByGenre = entityManager.createQuery("select m from movies m where m.genre = :genre", Movie.class)
                .setParameter("genre", genre)
                .getResultList();

        return movieByGenre;
    }
}
