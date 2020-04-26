package controllers;

import com.jfoenix.controls.JFXButton;
import domain.entities.Movie;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class BuyTicket {
    public JFXButton buy;
    public Spinner spinner;


    public void initialize(){
        spinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4)
        );
    }

    public void buy(ActionEvent actionEvent) {

        Movie movie = HomeController.movie;

        int getSpinnerValue = (int) spinner.getValue();

        if (getSpinnerValue > movie.getSeats()){
            Optional<ButtonType> alertError = new Alert(Alert.AlertType.ERROR, "Not enough available seats!").showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You successfully bought a ticket!");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            buy.getScene().getWindow().hide();
        }



        movie.setSeats(movie.getSeats() - getSpinnerValue);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistence");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(movie);


        entityManager.getTransaction().commit();

    }
}
