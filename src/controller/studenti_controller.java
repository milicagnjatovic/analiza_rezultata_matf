package controller;

import dataHandling.Data;
import dataHandling.PredmetiTableType;
import dataHandling.StudentiTableType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class studenti_controller implements Initializable {
    @FXML
    private VBox vb_scroll;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableView<StudentiTableType> tvStudenti = new TableView();
        tvStudenti.getStylesheets().add("/home/milica/Desktop/analiza_rezultata/src/resources/styles.css");

        TableColumn colIndkes = new TableColumn("indeks");
        colIndkes.setCellValueFactory(new PropertyValueFactory<>("indeks"));
        TableColumn colIme = new TableColumn("ime_prezime");
        colIme.setCellValueFactory(new PropertyValueFactory<>("ime_prezime"));
        TableColumn colPolozeno = new TableColumn("polozeno");
        colPolozeno.setCellValueFactory(new PropertyValueFactory<>("polozeno"));
        TableColumn colProsek = new TableColumn("prosek");
        colProsek.setCellValueFactory(new PropertyValueFactory<>("prosek"));

        tvStudenti.getColumns().addAll(colIndkes, colIme, colPolozeno, colProsek);
        tvStudenti.getColumns().forEach(c -> c.prefWidthProperty().bind(tvStudenti.widthProperty().divide(4.0)));

        tvStudenti.getItems().addAll(Data.studentiTableItems());
        vb_scroll.getChildren().add(tvStudenti);
    }
}
