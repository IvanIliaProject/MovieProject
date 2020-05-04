package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.entities.User;
import domain.entities.UserType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    public static User userAdmin;
    public static User user;

    public TextField email;
    public PasswordField password;
    public Button login;
    public Label label;
    public Button register;
    public ImageView fbIcon;
    public ImageView googleIcon;
    public ImageView twitterIcon;

    public void initialize(){
        Image imageFb = new Image("http://getdrawings.com/free-icon/icon-facebook-png-60.png");
        Image imageTwitter = new Image("https://pluspng.com/img-png/twitter-png-file-twitter-icon-png-256.png");
        Image imageGoogle = new Image("https://cdn3.iconfinder.com/data/icons/ultimate-social/150/34_google_plus-512.png");

        fbIcon.setImage(imageFb);
        twitterIcon.setImage(imageTwitter);
        googleIcon.setImage(imageGoogle);
    }


    public void login(ActionEvent actionEvent) throws IOException {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        String email1 = email.getText();
        String pass = password.getText();

        try {
            user = UserRepository.findUser(email1, pass);
            if (user != null) {
                if (user.getUserType().equals(UserType.ADMIN)) {
                    userAdmin = user;
                }
            }
            homeWindow();
        } catch (NoResultException nre) {
            label.setVisible(true);
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

    public void registerBack(ActionEvent actionEvent) throws IOException {

        register.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();
    }
}
