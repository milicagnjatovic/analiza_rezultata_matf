package controller;

import dataHandling.Data;
import dataHandling.PredmetiTableType;
import dataHandling.StudentiTableType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class poredjenjePredmetaController implements Initializable {
    @FXML
    private VBox vbScroll;

    @FXML
    private Label lblMinProlaznostPred;
    @FXML
    private Label lblMinProlaznostVal;
    @FXML
    private Label lblMaxProlaznostPred;
    @FXML
    private Label lblMaxProlaznostVal;
    @FXML
    private Label lblMinProsekPred;
    @FXML
    private Label lblMinProsekVal;
    @FXML
    private Label lblMaxProsekPred;
    @FXML
    private Label lblMaxProsekVal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Data.setInfoOPredmetima();

        lblMaxProlaznostVal.setText(String.format("%.2f", Data.maxProlaznost) + "%");
        lblMaxProlaznostPred.setText(Data.maxProlaznostP);
        lblMaxProsekVal.setText(String.format("%.2f", Data.maxProsek));
        lblMaxProsekPred.setText(Data.maxProsekP);

        lblMinProlaznostVal.setText(String.format("%.2f", Data.minProlaznost)+"%");
        lblMinProlaznostPred.setText(Data.minProlaznostP);
        lblMinProsekVal.setText(String.format("%.2f", Data.minProsek));
        lblMinProsekPred.setText(Data.minProsekP);

        CategoryAxis xProlaznost = new CategoryAxis();
        xProlaznost.setCategories(FXCollections.<String>observableArrayList(Data.getOznakePredmeta()));
        xProlaznost.setTickLabelRotation(270);
        NumberAxis yProlaznost = new NumberAxis();
        yProlaznost.setAutoRanging(false);
        yProlaznost.setUpperBound(100.0);
        BarChart<String, Double> bcProlaznost = new BarChart(xProlaznost, yProlaznost);
        bcProlaznost.setTitle("Prolaznost po predmetu");
        bcProlaznost.setPrefWidth(vbScroll.getPrefWidth());
        bcProlaznost.setPrefWidth(150);
        bcProlaznost.setCategoryGap(0.01);
        bcProlaznost.setBarGap(0.01);
        bcProlaznost.getData().addAll(Data.prolaznostSvakogPredmeta);
        vbScroll.getChildren().addAll(bcProlaznost);

        CategoryAxis xProsek = new CategoryAxis();
        xProsek.setCategories(FXCollections.<String>observableArrayList(Data.getOznakePredmeta()));
        xProsek.setTickLabelRotation(270);

        NumberAxis yProsek = new NumberAxis();
        yProsek.setAutoRanging(false);
        yProsek.setUpperBound(10.0);

        BarChart<String, Double> bcProsek = new BarChart(xProsek, yProsek);
        bcProsek.setTitle("Prosek po predmetu");
        bcProsek.setPrefWidth(vbScroll.getPrefWidth());
        bcProsek.setPrefWidth(150);
        bcProsek.setCategoryGap(0.01);
        bcProsek.setBarGap(0.01);
        bcProsek.getData().addAll(Data.prosekOcenaSvakogPredmeta);
        vbScroll.getChildren().addAll(bcProsek);

        TableView<PredmetiTableType> tvPredmeti = new TableView();
        tvPredmeti.getStylesheets().add("/home/milica/Desktop/analiza_rezultata/src/resources/styles.css");

        TableColumn colOznaka = new TableColumn("predmet");
        colOznaka.setCellValueFactory(new PropertyValueFactory<>("predmet"));
        TableColumn colSemestar = new TableColumn("semestar");
        colSemestar.setCellValueFactory(new PropertyValueFactory<>("semestar"));
        TableColumn colProsek = new TableColumn("prosek");
        colProsek.setCellValueFactory(new PropertyValueFactory<>("prosek"));
        TableColumn colProlaznost = new TableColumn("prolaznost");
        colProlaznost.setCellValueFactory(new PropertyValueFactory<>("prolaznost"));
        TableColumn col10 = new TableColumn("10");
        col10.setCellValueFactory(new PropertyValueFactory<>("o10"));
        TableColumn col9 = new TableColumn("9");
        col9.setCellValueFactory(new PropertyValueFactory<>("O9"));
        TableColumn col8 = new TableColumn("8");
        col8.setCellValueFactory(new PropertyValueFactory<>("O8"));
        TableColumn col7 = new TableColumn("7");
        col7.setCellValueFactory(new PropertyValueFactory<>("O7"));
        TableColumn col6 = new TableColumn("6");
        col6.setCellValueFactory(new PropertyValueFactory<>("O6"));

        tvPredmeti.getColumns().addAll(colOznaka, colSemestar, colProsek, colProlaznost, col10, col9, col8, col7, col6);
        tvPredmeti.getColumns().forEach(c -> c.prefWidthProperty().bind(tvPredmeti.widthProperty().divide(9.0)));

        tvPredmeti.getItems().addAll(Data.predmetiTableItems);
        vbScroll.getChildren().add(tvPredmeti);
    }
}
