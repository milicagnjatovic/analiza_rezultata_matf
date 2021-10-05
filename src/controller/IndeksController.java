package controller;

import dataHandling.Data;
import dataHandling.Ispit;
import dataHandling.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class IndeksController implements Initializable {
    @FXML
    private TreeView<String> tw_ispiti;

    @FXML
    private Label lblProsek;

    @FXML
    private Label lblIme;

    @FXML
    private Label lblOdnos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Student student = Data.student;
        if (student==null){
            lblIme.setText("Nepostojeci student");
            lblProsek.setText("-");
            lblOdnos.setText("-");
            return;
        }
        lblIme.setText(student.getIme() + " " + student.getPrezime() + " " + student.getIndeks());
        lblProsek.setText(String.format("%.2f", student.getProsek()));
        lblOdnos.setText(student.getOdnosPolozenih());

        TreeItem<String> root = new TreeItem<>("Godine");
        root.setExpanded(true);

        List<Ispit> ispiti = student.getIspiti();
        if (ispiti.size()==0)
            return;
        ispiti.sort(new Comparator<Ispit>() {
            @Override
            public int compare(Ispit i1, Ispit i2) {
                if (i1.getPredmet().getSemestar()<i2.getPredmet().getSemestar())
                    return -1;
                else if (i1.getPredmet().getSemestar()>i2.getPredmet().getSemestar())
                    return 1;
                return i1.getOcena()<i2.getOcena() ? 1 : -1;
            }
        });

        TreeItem<String> currIt = new TreeItem<>(ispiti.get(0).getPredmet().getGodina()+".");
        currIt.setExpanded(true);
        int currGodina = ispiti.get(0).getPredmet().getGodina();
        int sum = 0;
        int num = 0;
        int espb = 0;
        int polozeno = 0;
        for (Ispit i:ispiti) {
            if (i.getOcena() <= 5)
                continue;
            TreeItem<String> nItem = new TreeItem<>(i.getOcena() + " (" + i.getPoeni()+") " + i.getPredmet().getNaziv() + " ("+ i.getPredmet().getEspb() + " ESPB)");
            if (i.getPredmet().getGodina()==currGodina){
                num += 1;
                currIt.getChildren().add(nItem);
                if (i.getOcena()>=5){
                    sum += i.getOcena();
                    polozeno += 1;
                    espb += i.getPredmet().getEspb();
                }
            }
            else{
                currIt.setValue(currGodina + ". godina\t polozeno/upisano:" + polozeno + "/" + num +"\tprosek: " + String.format("%.2f ", sum*1.0/polozeno)  + "\t" + espb + "ESPB");

                num = 1;
                polozeno = 0;
                sum = 0;
                espb = 0;
                if(i.getOcena()>=5){
                    polozeno = 1;
                    sum = i.getOcena();
                    espb += i.getPredmet().getEspb();
                }

                root.getChildren().add(currIt);
                currIt = new TreeItem<>(i.getPredmet().getGodina()+"");
                currIt.setExpanded(true);
                currGodina = i.getPredmet().getGodina();
                currIt.getChildren().add(nItem);
            }
        }
        currIt.setValue(currGodina + ". godina\t polozeno/upisano:" + polozeno + "/" + num +"\tprosek: " + String.format("%.2f", sum*1.0/polozeno) + "\t" + espb + "ESPB");
        root.getChildren().add(currIt);
        tw_ispiti.setRoot(root);
        tw_ispiti.setShowRoot(false);
    }
}
