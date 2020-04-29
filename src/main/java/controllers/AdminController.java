package controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.entities.Movie;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import validations.Validation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.time.LocalDate;

public class AdminController {

    public JFXTextField title;
    public JFXTextField price;
    public JFXTextField seats;
    public JFXTextField imageURL;
    public DatePicker date;
    public JFXTextArea description;
    public Label priceLabel;
    public Label success;
    public Label seatsLabel;
    public Label titleLabel;

    public void addMovie(ActionEvent actionEvent) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistence");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        Movie movie = new Movie();

        LocalDate chosenDate = date.getValue();
        Date date = Date.valueOf(chosenDate);
        
        movie.setName(title.getText());
        movie.setDate(date);
        movie.setDescription(description.getText());
        movie.setPrice(Double.valueOf(price.getText()));
        movie.setImageUrl(imageURL.getText());
        movie.setSeats(Integer.parseInt(seats.getText()));
        movie.setCountSeats(0);
        movie.setUser(LoginController.userAdmin);



        if (movie.getName().length() < 1 || !Validation.validPrice(movie.getPrice()) || !Validation.isValidSeats(movie.getSeats())){

            if (movie.getName().length() < 1){
                titleLabel.setVisible(true);
            }else{
                titleLabel.setVisible(false);
            }

            if (!Validation.isValidSeats(movie.getSeats())){
                seatsLabel.setVisible(true);
            }else{
                seatsLabel.setVisible(false);
            }
            if (!Validation.validPrice(movie.getPrice())){
                priceLabel.setVisible(true);
            }else{
                priceLabel.setVisible(false);
            }
        }else {
            entityManager.persist(movie);
            entityManager.getTransaction().commit();
            success.setVisible(true);
        }


    }

    public void backToHome(ActionEvent actionEvent) {
    }
}
