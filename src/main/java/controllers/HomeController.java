package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
    public JFXComboBox comboBox;
    public Spinner searchByPrice;
    public JFXComboBox genreBox;


    @FXML
    private void initialize() {

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

        comboBox.getItems().addAll("Title", "Date", "Price");

        genreBox.getItems().addAll("Romantic", "Action", "Comedy", "Horror", "Adventure",
                "Crime", "Fantasy", "Animation");

        searchByPrice.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 30)
        );

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

    public void search(ActionEvent actionEvent) {
        String chosenItem = comboBox.getSelectionModel().getSelectedItem().toString();
        if (chosenItem.equals("Title")) {
            searchByTitle();
        } else if (chosenItem.equals("Date")) {
            searchByDate();
        }else if(chosenItem.equals("Price")){
            searchByPrice();
        }else{
            searchByGenre();
        }
    }

    public void chosenAction(ActionEvent actionEvent) {
        String combo = comboBox.getSelectionModel().getSelectedItem().toString();
        if (combo.equals("Title")) {
            searchField.setVisible(true);
            from.setVisible(false);
            to.setVisible(false);
            searchByPrice.setVisible(false);
        } else if (combo.equals("Date")) {
            searchField.setVisible(false);
            searchByPrice.setVisible(false);
            from.setVisible(true);
            to.setVisible(true);
        } else if (combo.equals("Price")){
            searchField.setVisible(false);
            from.setVisible(false);
            to.setVisible(false);
            searchByPrice.setVisible(true);
        }else{
            searchField.setVisible(false);
            from.setVisible(false);
            to.setVisible(false);
            searchByPrice.setVisible(false);
            genreBox.setVisible(true);
        }
    }

    private void searchByDate() {
        from.setVisible(true);
        to.setVisible(true);
        LocalDate fromDate = from.getValue();
        Date from = Date.valueOf(fromDate);

        LocalDate toDate = to.getValue();
        Date to = Date.valueOf(toDate);

        tableView.refresh();
        List<Movie> returned = tableView.getItems();
        tableView.getItems().removeAll(returned);
        tableView.refresh();
        List<Movie> findByDate = MovieRepository.findByDate(from, to);
        tableView.getItems().addAll((findByDate));
    }

    private void searchByTitle() {
        String title = searchField.getText();
        searchField.setVisible(true);
        List<Movie> returned = tableView.getItems();
        tableView.getItems().removeAll(returned);
        tableView.refresh();
        Movie findByTitle = MovieRepository.findMovieByTitle(title);
        tableView.getItems().addAll((findByTitle));
    }

    private void searchByGenre(){
        String genre = genreBox.getSelectionModel().getSelectedItem().toString();
        genreBox.setVisible(true);

        List<Movie> returned = tableView.getItems();
        tableView.getItems().removeAll(returned);
        tableView.refresh();
        List<Movie> findByGenre = MovieRepository.findByGenre(genre);
        tableView.getItems().addAll((findByGenre));
    }

    private void searchByPrice() {
        searchByPrice.setVisible(true);

        double spinnerValue = (double) searchByPrice.getValue();
        List<Movie> returned = tableView.getItems();
        tableView.getItems().removeAll(returned);
        tableView.refresh();
        List<Movie> findByPrice = MovieRepository.findByPrice(spinnerValue);
        tableView.getItems().addAll((findByPrice));
    }
}
