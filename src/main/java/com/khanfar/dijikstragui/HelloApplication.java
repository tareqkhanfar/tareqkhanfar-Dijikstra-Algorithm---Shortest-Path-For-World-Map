package com.khanfar.dijikstragui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);
        scene.getStylesheets().addAll(HelloApplication.class.getResource("style.css").toExternalForm());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {

        try {
            Dijkstra.csvFile() ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

     // Dijkstra.generateEdges();
        Dijkstra.startTable();
       Dijkstra. adjForEachVertixs();
        //  dijkstra.printPath(dijkstra.graph.get(0) , dijkstra.graph.get(5));


        launch();
    }

}