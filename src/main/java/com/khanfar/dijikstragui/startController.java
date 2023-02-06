package com.khanfar.dijikstragui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;

public class startController {
    public static String FILE_NAME = "" ;
    @FXML
    void generateEdgesOnAction(ActionEvent event) throws IOException {

         Dijkstra.generateEdges(FILE_NAME);
    }

    @FXML
    void readfileOnAction(ActionEvent event) throws IOException, InvalidFormatException {
        FileChooser fileChooser = new FileChooser()  ;
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
           FILE_NAME =  file.getAbsolutePath();
           Dijkstra.csvFile(FILE_NAME);
            Dijkstra.startTable(FILE_NAME);
        }
     }

    @FXML
    void startOnAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);
        scene.getStylesheets().addAll(HelloApplication.class.getResource("style.css").toExternalForm());
        Stage stage = new Stage() ;
        stage.setTitle("shortest path !");
        stage.setScene(scene);
        stage.show();
    }

}
