package controllers;

import com.jfoenix.controls.JFXTextField;
import domain.entities.Movie;
import javafx.event.ActionEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AdminController {


    public JFXTextField title;
    public JFXTextField price;
    public JFXTextField seats;
    public JFXTextField year;
    public JFXTextField description;
    public JFXTextField imageURL;

    public void addMovie(ActionEvent actionEvent) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistence");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        Movie movie = new Movie();

        movie.setName(title.getText());
        movie.setYear(year.getText());
        movie.setDescription(description.getText());
        movie.setPrice(Double.valueOf(price.getText()));
        movie.setImageUrl(imageURL.getText());
        movie.setSeats(Integer.parseInt(seats.getText()));

        entityManager.persist(movie);
        entityManager.getTransaction().commit();

    }

    public void backToHome(ActionEvent actionEvent) {
    }
}
