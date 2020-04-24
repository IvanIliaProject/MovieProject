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
import javafx.stage.Stage;
import repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class RegisterController {

    @FXML
    public JFXPasswordField password;
    @FXML
    public JFXTextField name;
    @FXML
    public JFXTextField email;
    @FXML
    public JFXPasswordField confirmPassword;
    @FXML
    public JFXTextField phone;
    @FXML
    public JFXTextField card;
    @FXML
    public JFXButton registerButton;

    public void register(ActionEvent actionEvent) throws IOException {

        String name1 = name.getText();
        String email1 = email.getText();
        String password1 = password.getText();
        String confirmPassword1 = confirmPassword.getText();
        String phone1 = phone.getText();
        String card1 = card.getText();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        User user = new User();

        user.setName(name1);
        user.setEmail(email1);
        user.setPassword(password1);
        user.setPhone(phone1);
        user.setCard(card1);


        if (UserRepository.findAll().size() == 0){
            user.setUserType(UserType.ADMIN);
        }else{
            user.setUserType(UserType.USER);
        }

        if (user.getPassword().equals(confirmPassword1)){
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }
        loginWindow();
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
