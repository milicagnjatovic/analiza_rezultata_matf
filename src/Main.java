import dataHandling.Data;
import dataHandling.Predmet;
import dataHandling.StudentiTableType;
import dataHandling.UnosIspita;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import javafx.application.Application;



public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("src/resources/layout.fxml"));

        stage.setTitle("Analiza rezultata");
        stage.setScene(new Scene(root));

        stage.setResizable(false);

        stage.show();
    }
}
