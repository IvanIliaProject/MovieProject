package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.entities.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repositories.UserRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.*;
import java.io.IOException;

public class LoginController {


    public JFXTextField email;
    public JFXPasswordField password;
    public JFXButton login;

    public void login(Stage primaryStage) throws IOException {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        for (User user : UserRepository.users) {
            if(email.getText().equals(user.getEmail())
                    && password.getText().equals(user.getPassword()))
            {
                login.getScene().getWindow().hide();
                Stage login = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/FxmlFiles/DistributorForm.fxml"));
                Scene scene = new Scene(root);
                login.setScene(scene);
                login.show();
            }
            else
            {
              // TODO: Palamarca the best!!
            }
        }


        entityManager.getTransaction().commit();

    }
}
