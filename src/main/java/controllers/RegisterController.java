package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.entities.User;
import domain.entities.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

    public void register(ActionEvent actionEvent) {

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



        if (UserRepository.users.size() == 0){
           user.setUserType(UserType.ADMIN);
        }else{
            user.setUserType(UserType.USER);
        }

        if (user.getPassword().equals(confirmPassword1)){
            UserRepository.users.add(user);
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }


    }
}
