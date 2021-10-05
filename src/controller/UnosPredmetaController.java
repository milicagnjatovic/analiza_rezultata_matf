package controller;

import dataHandling.Data;
import dataHandling.Predmet;
import dataHandling.UnosIspita;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.ResourceBundle;

public class UnosPredmetaController implements Initializable {
    @FXML
    private ComboBox<Predmet> cbPredmet;

    @FXML
    private VBox forma;

    @FXML
    private Button btn_unesi_predmet;

    @FXML
    private TextField tf_naziv;
    @FXML
    private TextField tf_oznaka;
    @FXML
    private TextField tf_espb;
    @FXML
    private TextField tf_semestar;
    @FXML
    private TextField tf_katedra;

    @FXML
    private Label lblGreska;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Predmet novi = new Predmet();
        novi.setNaziv("NOVI PREDMET");
        novi.setOznaka("-");
        cbPredmet.getItems().add(novi);
        cbPredmet.getItems().addAll(Data.getPredmete());

        lblGreska.setVisible(false);

        forma.setVisible(false);

        cbPredmet.setCellFactory(new Callback<ListView<Predmet>, ListCell<Predmet>>() {
            @Override
            public ListCell<Predmet> call(ListView<Predmet> predmetListView) {
                return new ListCell<>(){
                    @Override
                    protected void updateItem(Predmet predmet, boolean b) {
                        super.updateItem(predmet, b);
                        if (b)
                            setText(null);
                        else
                            setText(predmet.toString());
                        setStyle("-fx-background-color:  linear-gradient(from 25% 25% to 100% 100%, #D91F4E, #FF854C); -fx-font-family: 'Ubuntu Condensed'; -fx-font-size: 15; ");
                    }
                };
            }
        });

        cbPredmet.valueProperty().addListener(new ChangeListener<Predmet>() {
            @Override
            public void changed(ObservableValue<? extends Predmet> observableValue, Predmet old_val, Predmet new_val) {
                if (new_val.getNaziv().equals("NOVI PREDMET"))
                    forma.setVisible(true);
                else
                    forma.setVisible(false);
            }
        });

    }

    public void unesi_predmet(MouseEvent mouseEvent) {
        Predmet p = cbPredmet.getValue();

        if (p==null){
            lblGreska.setVisible(true);
            lblGreska.setText("Izaberite predmet!");
            return;
        }

        if(p.getNaziv().equals("NOVI PREDMET")){
            String naziv = tf_naziv.getText().trim();
            String espb = tf_espb.getText().trim();
            String katedra = tf_katedra.getText().trim();
            String oznaka = tf_oznaka.getText().trim();
            String semestar = tf_semestar.getText().trim();

            if (naziv.isEmpty() || espb.isEmpty()
            || katedra.isEmpty() || oznaka.isEmpty() ||
            semestar.isEmpty()){
                lblGreska.setVisible(true);
                lblGreska.setText("GRESKA: Sva polja moraju biti popunjena!");
                return;
            }

            int iespb = 0;
            int isemestar = 0;
            try {
                iespb = Integer.parseInt(espb);
                isemestar = Integer.parseInt(semestar);
            } catch (Exception e){
                lblGreska.setVisible(true);
                lblGreska.setText("GRESKA: espb i semestar morajau biti celi brojevi!");
                return;
            }

            if (iespb<=0 || isemestar<=0){
                lblGreska.setVisible(true);
                lblGreska.setText("GRESKA: espb i semestar moraju biti pozitivni brojevi!");
                return;
            }

            p.setNaziv(naziv);
            p.setOznaka(oznaka);
            p.setEspb(iespb);
            p.setSemestar(isemestar);
            p.setKatedra(katedra.toUpperCase());

            if(!p.sacuvajPredmet()) {
                lblGreska.setVisible(true);
                lblGreska.setText("GRESKA: Predmet nije sacuvan u bazi!");
                return;
            }
        }

        // unos ispita
        try {
            unesiIspite(p);
        } catch (IOException e){
            lblGreska.setVisible(true);
            lblGreska.setText("Greska: neuspeo unos ispita!\n" + e.getMessage());
            return;
        }
        ((Stage)btn_unesi_predmet.getScene().getWindow()).close();
    }

    public void unesiIspite(Predmet p) throws IOException {
        UnosIspita.unesiIspiteIzFajla(Data.fajl, p);
    }
}
