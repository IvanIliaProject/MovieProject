package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.entities.User;
import domain.entities.UserType;
import javafx.event.ActionEvent;
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
import repositories.UserRepository;
import validations.Validation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class RegisterController {


    @FXML
    public TextField name;
    @FXML
    public TextField email;
    @FXML
    public TextField phone;
    @FXML
    public Button registerButton;
    public Label emailLabel;
    public Label passwordLabel;
    public Label confirmLabel;
    public PasswordField pass;
    public PasswordField confirmPass;
    public Label nameLabel;
    public ImageView fbIcon;
    public ImageView twitterIcon;
    public ImageView googleIcon;

    public void initialize(){
        Image imageFb = new Image("http://getdrawings.com/free-icon/icon-facebook-png-60.png");
        Image imageTwitter = new Image("https://pluspng.com/img-png/twitter-png-file-twitter-icon-png-256.png");
        Image imageGoogle = new Image("https://cdn3.iconfinder.com/data/icons/ultimate-social/150/34_google_plus-512.png");

        fbIcon.setImage(imageFb);
        twitterIcon.setImage(imageTwitter);
        googleIcon.setImage(imageGoogle);
    }


    public void register(ActionEvent actionEvent) throws IOException {

        String name1 = name.getText();
        String email1 = email.getText();
        String password1 = pass.getText();
        String confirmPassword1 = confirmPass.getText();
        String phone1 = phone.getText();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        User user = new User();

        user.setName(name1);
        user.setEmail(email1);

        user.setPassword(password1);
        user.setPhone(phone1);


        if (UserRepository.findAll().size() == 0) {
            user.setUserType(UserType.ADMIN);
        } else {
            user.setUserType(UserType.USER);
        }

        if (user.getName().length() < 2 || !Validation.validEmail(user.getEmail()) || !Validation.isValidPassword(user.getPassword())
                || !user.getPassword().equals(confirmPassword1)) {

            if (user.getName().length() < 2){
                nameLabel.setVisible(true);
            }else{
                nameLabel.setVisible(false);
            }

            if (!Validation.validEmail(user.getEmail())) {
                emailLabel.setVisible(true);
            }else{
                emailLabel.setVisible(false);
            }
            if (!Validation.isValidPassword(user.getPassword())) {
                passwordLabel.setVisible(true);
            }else{
                passwordLabel.setVisible(false);
            }
            if (!user.getPassword().equals(confirmPassword1)) {
                confirmLabel.setVisible(true);
            }else{
                confirmLabel.setVisible(false);
            }
        }else{
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            loginWindow();
        }


    }

    private void loginWindow() throws IOException {
        registerButton.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();
    }
}
