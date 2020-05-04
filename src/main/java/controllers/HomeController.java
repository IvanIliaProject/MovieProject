package controllers;

import com.jfoenix.controls.*;
import domain.entities.Movie;
import domain.entities.User;
import domain.entities.UserType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import repositories.MovieRepository;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HomeController {

    public ObservableList<Movie> movies;
    public static Movie movie;

    public Button admin;

    public TableView<Movie> tableView = new TableView<>();
    public TableColumn<Movie, String> titleColumn;
    public TableColumn<Movie, Double> priceColumn;
    public TableColumn<Movie, String> yearColumn;
    public ImageView imgView;
    public Button buy;
    public DatePicker from;
    public DatePicker to;
    public Button search;
    public JFXTextField searchField;
    public JFXComboBox comboBox;
    public JFXComboBox genreBox;
    public Button top;
    public TableColumn genreColumn;
    public Label movieTitle;
    public Label ticketsLabel;
    public TextArea description;
    public Button signOut;
    public Button delete;
    public Spinner spinnerFrom;
    public Spinner spinnerTo;
    public Label priceFrom;
    public Label priceTo;


    @FXML
    private void initialize() {

        if (LoginController.user.getUserType().equals(UserType.ADMIN)) {
            admin.setVisible(true);
            delete.setVisible(true);
        } else {
            admin.setVisible(false);
            delete.setVisible(false);
        }

        spinnerFrom.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 30)
        );
        spinnerTo.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 30)
        );

        User user = LoginController.user;


        movies = FXCollections.observableArrayList(MovieRepository.findAllMovie());

        titleColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Movie, Double>("price"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("date"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("genre"));


        tableView.getItems().addAll(movies);

        comboBox.getItems().addAll("Title", "Genre", "Price");

        genreBox.getItems().addAll("Romantic", "Action", "Comedy", "Horror", "Adventure",
                "Crime", "Fantasy", "Animation", "Drama");

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
                    ticketsLabel.setText(String.valueOf(movie.getSeats() - movie.getCountSeats()));
                    movieTitle.setText(movie.getName());
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
            spinnerFrom.setVisible(false);
            spinnerTo.setVisible(false);
            priceFrom.setVisible(false);
            priceTo.setVisible(false);
            genreBox.setVisible(false);
        } else if (combo.equals("Price")){
            searchField.setVisible(false);
            spinnerFrom.setVisible(true);
            spinnerTo.setVisible(true);
            priceFrom.setVisible(true);
            priceTo.setVisible(true);
            genreBox.setVisible(false);
        }else{
            searchField.setVisible(false);
            spinnerFrom.setVisible(false);
            spinnerTo.setVisible(false);
            genreBox.setVisible(true);
            priceFrom.setVisible(false);
            priceTo.setVisible(false);
        }
    }
    public void searchByDate() {

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

        double fromValue = (double) spinnerFrom.getValue();
        double toValue = (double) spinnerTo.getValue();

        List<Movie> returned = tableView.getItems();
        tableView.getItems().removeAll(returned);
        tableView.refresh();
        List<Movie> findByPrice = MovieRepository.findByPrice(fromValue, toValue);
        tableView.getItems().addAll((findByPrice));
    }

    public void mostViewedMovies(ActionEvent actionEvent) {
        List<Movie> sortedList = movies.stream()
                .sorted(Comparator.comparingInt(Movie::getCountSeats).reversed())
                .collect(Collectors.toList());
        List<Movie> returned = tableView.getItems();
        tableView.getItems().removeAll(returned);
        tableView.refresh();
        tableView.getItems().addAll((sortedList));
    }

    public void signOut(ActionEvent actionEvent) throws IOException {

        buy.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();

    }

    public void deleteMovie(ActionEvent actionEvent) {
    }
}
