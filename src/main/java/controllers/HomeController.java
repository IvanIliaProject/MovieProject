package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import domain.entities.Movie;
import domain.entities.User;
import domain.entities.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import repositories.MovieRepository;
import repositories.UserRepository;

import java.io.IOException;
import java.util.List;

public class HomeController {


    public JFXButton admin;

    public JFXListView listView;



    public void initialize(){

      ObservableList<Movie> movies = FXCollections.observableArrayList(MovieRepository.findAllMovie());
        for (Movie movie : movies) {
            Image image = new Image(movie.getImageUrl());
            ImageView imageView = new ImageView();

            listView.getItems().addAll(FXCollections.observableArrayList(movie.getName() + "         "
                    + movie.getCountSeats() + "            "
                    + movie.getPrice()  + "         " + movie.getYear() + "         " + movie.getDescription())+ "         "
                    + imageView.setImage();
        }



    }


    public void adminWindow(ActionEvent actionEvent) throws IOException {
        admin.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-window.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();

    }
}
