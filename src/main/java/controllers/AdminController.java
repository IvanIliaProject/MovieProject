package controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.entities.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import validations.Validation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AdminController {

    public TextField title;
    public TextArea imageURL;
    public DatePicker date;
    public TextArea description;
    public Label priceLabel;
    public Label success;
    public Label seatsLabel;
    public Label titleLabel;
    public Spinner price;
    public ComboBox genre;
    public Spinner seats;
    public Button backHome;


    public void initialize(){
        price.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 30)
        );
        seats.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(15,50)
        );

        genre.getItems().addAll("Romantic", "Action", "Comedy", "Horror", "Adventure",
                "Crime", "Fantasy", "Animation", "Drama");
    }

    public void addMovie(ActionEvent actionEvent) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistence");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        Movie movie = new Movie();

        LocalDate chosenDate = date.getValue();
        Date date = Date.valueOf(chosenDate);

        String genreBox = genre.getSelectionModel().getSelectedItem().toString();

        movie.setName(title.getText());
        movie.setDate(date);
        movie.setDescription(description.getText());
        movie.setPrice((Double) price.getValue());
        movie.setImageUrl(imageURL.getText());
        movie.setSeats((Integer) seats.getValue());
        movie.setGenre(genreBox);
        movie.setCountSeats(0);
        movie.setUser(LoginController.userAdmin);



        if (movie.getName().length() < 1 ||  !Validation.validPrice(movie.getPrice()) || !Validation.isValidSeats(movie.getSeats())){

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

    public void backToHome(ActionEvent actionEvent) throws IOException {
        backHome.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();

    }
}
