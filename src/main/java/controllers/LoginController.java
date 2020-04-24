package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.entities.User;
import domain.entities.UserType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.*;
import repositories.UserRepository;

public class LoginController {

    public static List<User> loggedUsers = new ArrayList<>();

    public JFXTextField email;
    public JFXPasswordField password;
    public JFXButton login;

    public void login(ActionEvent actionEvent) throws IOException {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        String email1 = email.getText();
        String pass = password.getText();

        User user = UserRepository.findUser(email1, pass);

        if (user != null){
            if (user.getUserType().equals(UserType.ADMIN)) {
                homeWindow();
            }else{
                System.out.println("You are not admin");
            }
        }else{
            // TODO: invalid user label
        }

    }
        private void homeWindow() throws IOException {
            login.getScene().getWindow().hide();
            Stage login = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
            Scene scene = new Scene(root);
            login.setScene(scene);
            login.show();
        }

}
