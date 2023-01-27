package com.khanfar.dijikstragui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
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