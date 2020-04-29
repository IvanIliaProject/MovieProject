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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repositories.MovieRepository;
import repositories.UserRepository;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class HomeController {

    public ObservableList<Movie> movies;
    public static Movie movie;

    public JFXButton admin;

    public TableView<Movie> tableView = new TableView<>();
    public TableColumn<Movie, String> titleColumn;
    public TableColumn<Movie, Double> priceColumn;
    public TableColumn<Movie, String> yearColumn;
    public ImageView imgView;
    public Label description;
    public JFXButton buy;
    public DatePicker from;
    public DatePicker to;
    public Button search;
    public JFXTextField searchField;


    @FXML
    public void initialize() {

        if (LoginController.user.getUserType().equals(UserType.ADMIN)) {
            admin.setVisible(true);
        } else {
            admin.setVisible(false);
        }

        movies = FXCollections.observableArrayList(MovieRepository.findAllMovie());

        titleColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Movie, Double>("price"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("date"));


        tableView.getItems().addAll(movies);


        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (tableView.getSelectionModel().getSelectedItem() != null) {
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

    public void searchByDate(ActionEvent actionEvent) {


        LocalDate fromDate = from.getValue();
        Date from = Date.valueOf(fromDate);

        LocalDate toDate = to.getValue();
        Date to = Date.valueOf(toDate);

        List<Movie> findByDate = MovieRepository.findByDate(from, to);

        FilteredList<Movie> filteredList = new FilteredList<>(movies, m -> true);
        filteredList.setPredicate(movie1 -> {

            Date date = null;
            for (Movie movieByDate : findByDate) {
                date = movieByDate.getDate();
            }
            if (date != null) {
                if (date.compareTo(movie1.getDate()) == 0) {
                    return true;
                }
            }
            return false;

        });
        SortedList sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedList);
    }
   public void searchByTitle(KeyEvent keyEvent) {

       /* //Movie movie1 = MovieRepository.findMovieByTitle();

        FilteredList<Movie> filterMovie = new FilteredList<>(movies, m-> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterMovie.setPredicate((Predicate<? super Movie>)(Movie movie1)-> {
               movie1 = MovieRepository.findMovieByTitle(newValue);
              //  String lowerCase = movieTitle.getName().toLowerCase();
                if (newValue.isEmpty()) {
                    return true;
                }else if (movie1.getName().contains(newValue)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Movie> sortedList = new SortedList<>(filterMovie);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);


    }
*/
   }
}
