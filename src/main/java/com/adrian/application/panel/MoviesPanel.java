package com.adrian.application.panel;

import com.adrian.application.impl.MovieServiceImpl;
import com.adrian.application.service.MovieService;
import com.adrian.application.viewmodel.MoviesViewModel;
import com.adrian.domain.entities.Movie;
import com.adrian.infrastructure.persistence.PersistenceContext;
import com.adrian.infrastructure.persistence.impl.MovieDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class MoviesPanel {

    @FXML private TableView<Movie> tblMovies;
    @FXML private TableColumn<Movie, Long>   colId;
    @FXML private TableColumn<Movie, String> colTitle;
    @FXML private TableColumn<Movie, Integer> colDuration;
    @FXML private TableColumn<Movie, String> colGenre;
    @FXML private TableColumn<Movie, String> colRating;
    @FXML private TableColumn<Movie, String> colDescription;
    @FXML private TableColumn<Movie, String> colPoster;

    @FXML private Button btnAdd, btnEdit, btnDelete;

    private final MoviesViewModel vm;

    public MoviesPanel() {
        var ctx = new PersistenceContext(
            new MovieDaoImpl(),
            null,null,null,null,null,null,null,null,null,null
        );
        MovieService svc = new MovieServiceImpl(ctx);
        this.vm = new MoviesViewModel(svc);
    }

    @FXML
    public void initialize() {
        colId         .setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle      .setCellValueFactory(new PropertyValueFactory<>("title"));
        colDuration   .setCellValueFactory(new PropertyValueFactory<>("duration"));
        colGenre      .setCellValueFactory(new PropertyValueFactory<>("genre"));
        colRating     .setCellValueFactory(new PropertyValueFactory<>("rating"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPoster     .setCellValueFactory(new PropertyValueFactory<>("posterImageUrl"));

        tblMovies.setItems(vm.getMovies());
        vm.loadMovies();

        tblMovies.getSelectionModel().selectedItemProperty()
            .addListener((obs, oldSel, newSel) -> vm.setSelectedMovie(newSel));

        btnEdit.disableProperty().bind(
            tblMovies.getSelectionModel().selectedItemProperty().isNull()
        );
        btnDelete.disableProperty().bind(
            tblMovies.getSelectionModel().selectedItemProperty().isNull()
        );

        btnAdd   .setOnAction(e -> openDialog(null));
        btnEdit  .setOnAction(e -> openDialog(vm.getSelectedMovie()));
        btnDelete.setOnAction(e -> vm.deleteSelected());
    }

    private void openDialog(Movie movie) {
        Dialog<Movie> dlg = new Dialog<>();
        dlg.setTitle(movie == null ? "Add Movie" : "Edit Movie");
        ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dlg.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

        TextField tfTitle       = new TextField();
        TextField tfDuration    = new TextField();
        TextField tfGenre       = new TextField();
        TextField tfRating      = new TextField();
        TextArea  taDescription = new TextArea();
        TextField tfPoster      = new TextField();

        if (movie != null) {
            tfTitle      .setText(movie.getTitle());
            tfDuration   .setText(String.valueOf(movie.getDuration()));
            tfGenre      .setText(movie.getGenre());
            tfRating     .setText(movie.getRating());
            taDescription.setText(movie.getDescription());
            tfPoster     .setText(movie.getPosterImageUrl());
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Title:"),       tfTitle);
        grid.addRow(1, new Label("Duration:"),    tfDuration);
        grid.addRow(2, new Label("Genre:"),       tfGenre);
        grid.addRow(3, new Label("Rating:"),      tfRating);
        grid.addRow(4, new Label("Description:"), taDescription);
        grid.addRow(5, new Label("Poster URL:"),  tfPoster);

        dlg.getDialogPane().setContent(grid);

        dlg.setResultConverter(bt -> {
            if (bt == saveBtn) {
                if (tfTitle.getText().isBlank() || tfDuration.getText().isBlank()) {
                    new Alert(Alert.AlertType.ERROR, "Title та Duration обов'язкові").showAndWait();
                    return null;
                }
                int dur;
                try {
                    dur = Integer.parseInt(tfDuration.getText().trim());
                } catch (NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Duration має бути числом").showAndWait();
                    return null;
                }
                Movie m = (movie == null) ? new Movie() : movie;
                m.setTitle(tfTitle.getText().trim());
                m.setDuration(dur);
                m.setGenre(tfGenre.getText().trim());
                m.setRating(tfRating.getText().trim());
                m.setDescription(taDescription.getText().trim());
                m.setPosterImageUrl(tfPoster.getText().trim());
                return m;
            }
            return null;
        });

        dlg.showAndWait().ifPresent(m -> {
            if (movie == null) vm.addMovie(m);
            else            vm.updateMovie(m);
        });
    }
}
