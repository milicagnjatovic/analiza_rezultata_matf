package controller;

import dataHandling.Data;
import dataHandling.Predmet;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ListView<Predmet> listView;

    @FXML
    private Label nazivPredmeta;

    @FXML
    private Label espb;

    @FXML
    private Label semestar;

    @FXML
    private Label upisalo;

    @FXML
    private Label polozilo;

    @FXML
    private Label prosek;

    @FXML
    private PieChart pieOcene;

    @FXML
    private PieChart pieIndeksi;

    @FXML
    private TextField tfIndeks;

    @FXML
    private VBox vb_indeks_ocene;

    @FXML
    private VBox vb_poeni;

    @FXML
    private Label lblOcene;

    StackedBarChart<String, Integer> sbIneksOcene = null;
    LineChart<Number, Number> lineChart = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getItems().add(Data.sviPredmeti);
        listView.getItems().addAll(Data.getPredmete());

        listView.setCellFactory(new Callback<ListView<Predmet>, ListCell<Predmet>>() {
            @Override
            public ListCell<Predmet> call(ListView<Predmet> predmetListView) {
                return new ListCell<>(){
                    @Override
                    protected void updateItem(Predmet predmet, boolean b) {
                        super.updateItem(predmet, b);
                        setStyle("-fx-background-color: transparent; -fx-font-family: 'Ubuntu Condensed'; -fx-font-size: 20;");
//                        if (b)
//                            setText(null);
                        if(!b) {
                            setText(predmet.getOznaka());
                            if (predmet.getKatedra() != null) {
                                if (predmet.getKatedra().equalsIgnoreCase("m"))
                                    setStyle("-fx-font-style: bold;-fx-background-color: transparent; -fx-font-family: 'Ubuntu Condensed'; -fx-font-size: 20;");
                                else if (predmet.getKatedra().equalsIgnoreCase("i"))
                                    setStyle("-fx-font-style:italic; -fx-background-color: transparent; -fx-font-family: 'Ubuntu Condensed'; -fx-font-size: 20;");
                            }
                        }
                    }
                };
            }
        });

        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listView.getSelectionModel().select(0);

        this.nazivPredmeta.setText(Data.trenutniPredmet.getNaziv());
        espb.setText("-");
        semestar.setText("-");
        upisalo.setText(Data.upisaloSve()+"");
        polozilo.setText(Data.poloziloSve()+"");
        prosek.setText(String.format("%.2f", Data.prosekSvih())+"");
        pieOcene.dataProperty().setValue(Data.ocenePieData(null));
        pieOcene.setAnimated(false);
        pieIndeksi.dataProperty().setValue(Data.indeksiPieData(null));
        pieIndeksi.setLegendVisible(false);
        pieIndeksi.setAnimated(false);
        lblOcene.setText(Data.getLastPieString());

        listView.setOnMouseClicked(e -> {
            setScene();
        });

        CategoryAxis xIndeksOcene = new CategoryAxis();
        xIndeksOcene.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("palo", "6", "7", "8", "9", "10")));
        xIndeksOcene.setLabel("ocene");

        Axis yIndeksOcene = new NumberAxis();
        yIndeksOcene.setLabel("broj ocena");
//        yIndeksOcene.setAutoRanging(true);

        sbIneksOcene = new StackedBarChart<String, Integer>(xIndeksOcene, yIndeksOcene);
        sbIneksOcene.getData().addAll(Data.IndeksOceneData(null));
        sbIneksOcene.setAnimated(false);
        sbIneksOcene.getStylesheets().add("/home/milica/Desktop/analiza_rezultata/src/resources/styles.css");


        vb_indeks_ocene.getChildren().add(sbIneksOcene);

        NumberAxis xPoeni = new NumberAxis();
        xPoeni.setLabel("Poeni");
        xPoeni.setAutoRanging(false);
        xPoeni.setUpperBound(105);
        xPoeni.setLowerBound(45);
        NumberAxis yPoeni = new NumberAxis();
        yPoeni.setLabel("Broj studenata");
        yPoeni.setAutoRanging(false);

        lineChart = new LineChart<>(xPoeni, yPoeni);
        lineChart.setAnimated(false);
        lineChart.getData().addAll(Data.PoeniData(null));
        yPoeni.setUpperBound(Data.poeniUpperBound);
        lineChart.setLegendVisible(false);
        vb_poeni.getChildren().add(lineChart);
    }

    private void setScene() {
        if (listView.getSelectionModel().getSelectedItem().getId()==Data.trenutniPredmet.getId())
            return;
        Data.trenutniPredmet = listView.getSelectionModel().getSelectedItem();
        this.nazivPredmeta.setText(Data.trenutniPredmet.getNaziv());

        sbIneksOcene.getData().retainAll();
        lineChart.getData().retainAll();

        lineChart.getXAxis().setAutoRanging(false);
        lineChart.getYAxis().setAutoRanging(false);

        if(Data.trenutniPredmet.getOznaka().equals("SVI")){
            espb.setText("-");
            semestar.setText("-");
            upisalo.setText(Data.upisaloSve()+"");
            polozilo.setText(Data.poloziloSve()+"");
            prosek.setText(String.format("%.2f", Data.prosekSvih())+"");
            pieOcene.dataProperty().setValue(Data.ocenePieData(null));
            pieIndeksi.dataProperty().setValue(Data.indeksiPieData(null));
            sbIneksOcene.getData().addAll(Data.IndeksOceneData(null));
            lineChart.getData().addAll(Data.PoeniData(null));
        }
        else{
            espb.setText(Data.trenutniPredmet.getEspb()+" ESPB");
            semestar.setText(Data.trenutniPredmet.getSemestar()+". semestar");
            upisalo.setText(Data.trenutniPredmet.getUpisalo()+"");
            polozilo.setText(Data.trenutniPredmet.getPolozilo()+"");
            prosek.setText(String.format("%.2f", Data.trenutniPredmet.getProsek())+"");
            pieOcene.dataProperty().setValue(Data.ocenePieData(Data.trenutniPredmet));
            pieIndeksi.dataProperty().setValue(Data.indeksiPieData(Data.trenutniPredmet));
            sbIneksOcene.getData().addAll(Data.IndeksOceneData(Data.trenutniPredmet));
            lineChart.getData().addAll(Data.PoeniData(Data.trenutniPredmet));
        }
        ((NumberAxis)lineChart.getYAxis()).setUpperBound(Data.poeniUpperBound);
        lblOcene.setText(Data.getLastPieString());
    }

    public void unesi_u_bazu(MouseEvent mouseEvent) throws Exception {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog((Stage) espb.getScene().getWindow());
        if (file==null)
            return;
        Data.fajl = file.getAbsolutePath();

        if (Data.fajl==null)
            return;

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/resources/unos_predmeta.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Unos ispita iz " + Data.fajl);
        stage.setScene(new Scene(root, 600, 400));

        stage.show();
    }

    public void pogledaj_indeks(MouseEvent mouseEvent) throws IOException {
        Data.setStudent(tfIndeks.getText().trim());

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/resources/indeks.fxml"));
        Stage stage = new Stage();
        stage.setTitle("indeks");
        stage.setScene(new Scene(root, 600, 400));

        stage.show();
    }

    public void prikazi_izvestaj(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/resources/poredjenje_predmeta.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Izvestaj o svim predmetima");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void prikazi_izvestaj_studenti(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/resources/studenti.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Izvestaj o studentima");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
