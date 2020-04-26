package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import domain.entities.Movie;
import domain.entities.User;
import domain.entities.UserType;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repositories.MovieRepository;
import repositories.UserRepository;

import java.io.IOException;
import java.util.List;

public class HomeController {

    public static Movie movie;

    public JFXButton admin;

    public TableView<Movie> tableView = new TableView<>();
    public TableColumn<Movie, String> titleColumn;
    public TableColumn<Movie, Double> priceColumn;
    public TableColumn<Movie, String> yearColumn;
    public ImageView imgView;
    public Label description;
    public JFXButton buy;

    @FXML
    public void initialize() {

        if (LoginController.user.getUserType().equals(UserType.ADMIN)){
            admin.setVisible(true);
        }else{
            admin.setVisible(false);
        }

        ObservableList<Movie> movies = FXCollections.observableArrayList(MovieRepository.findAllMovie());

        titleColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Movie, Double>("price"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("year"));

        tableView.getItems().addAll(movies);


        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(tableView.getSelectionModel().getSelectedItem() != null)
                {
                    TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    Object val = tablePosition.getTableColumn().getCellData(newValue);

                    String title = val.toString();

                    movie = MovieRepository.findMovieByTitle(title);
                    Image image = new Image(movie.getImageUrl());
                    imgView.setImage(image);
                    description.setText(movie.getDescription());

                }
            }
        });

        
    }
    public void adminWindow(ActionEvent actionEvent) throws IOException {
        admin.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin-window.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();

    }

    public void buyTicket(ActionEvent actionEvent) throws IOException {

        buy.getScene().getWindow();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/buy-ticket.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();

    }
}
