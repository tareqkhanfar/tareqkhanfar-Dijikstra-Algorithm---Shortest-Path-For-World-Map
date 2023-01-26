package com.khanfar.dijikstragui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.fxml.FXML;


import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;

import static java.awt.PageAttributes.ColorType.COLOR;

public class HelloController implements Initializable {
    @FXML
    private ComboBox<String> dest;

    @FXML
    private TextField distance;

    @FXML
    private  AnchorPane home;

    @FXML
    private  ImageView map;

    @FXML
    private TextArea path;

    @FXML
    private ComboBox<String> src;


public static AnchorPane home2 ;
public static ImageView map2 ;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        map2 = map ;
        home2 = home;

        for (Vertex vertex : Dijkstra.graph) {

            src.getItems().addAll(vertex.getName());
            dest.getItems().addAll(vertex.getName());


            Button button = new Button(vertex.getName());
            button.setOnAction(e -> {
                System.out.println(button.getText());


            });
            Point2D point2D = getXY(vertex.getX() , vertex.getY());
            button.setLayoutX(point2D.getX());
            button.setLayoutY(point2D.getY());
            button.setStyle(
                    "-fx-background-radius: 10em; " +
                            "-fx-min-width: 15px; " +
                            "-fx-min-height: 15px; " +
                            "-fx-max-width: 15px; " +
                            "-fx-max-height: 15px;" +
                            "-fx-background-color: red"
            );

            home.getChildren().addAll(button);

        }

        for (Vertex v : Dijkstra.graph) {
            for (Map.Entry<String, Vertex> w : v.adjace.entrySet()) {
                System.out.println("tareq");
                Point2D point2D = getXY(v.getX() , v.getY());
                Point2D  point2D1 = getXY(w.getValue().getX() , w.getValue().getY());
                Line line =new Line(point2D.getX() , point2D.getY(), point2D1.getX() , point2D1.getY());
                home.getChildren().addAll(line);

            }
        }




    }
    static Vertex  current  ;
   public static  LinkedList<Line> list = new LinkedList<>();
    public static String printPath(Vertex start, Vertex end) {
        current = null ;
        StringBuilder str= new StringBuilder();
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                   for (Line n : list) {
                           home2.getChildren().removeAll(n);
                           n.setVisible(false);

                   }
                }
            });
             current = end ;
             Vertex prev = current.getPV() ;

            while (prev != null) {
                str.append("   " + current.getName()  + "  And cost : " + current.getDV() + "----->" + prev.getName()  + "  And cost : " + prev.getDV());


                System.out.print("   " + current.getName()  + "  And cost : " + current.getDV() + "----->" + prev.getName()  + "  And cost : " + prev.getDV());
                Point2D point2D = getXY(current.getX() , current.getY());
                current = current.getPV();
                Point2D  point2D1 = getXY(prev.getX() , prev.getY());

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Line line =new Line(point2D.getX() , point2D.getY(), point2D1.getX() , point2D1.getY());
                        line.setFill(Color.RED);
                        line.setStroke(Color.BLUE);
                        line.setStrokeWidth(10);
                        Label  label = new Label("Tareq") ;
                        label.setFont(new Font(40));
                        list.add(line);
                        home2.getChildren().addAll(line , label);
                    }
                });
                current = prev;
                prev = current.getPV();

            }


        }
        catch (NullPointerException e ) {
            e.printStackTrace();
        }
        return str.toString() ;

    }

    @FXML
    void runOnAction(ActionEvent event) {
        Dijkstra.dijisktra(0);

        String s =  printPath(Dijkstra.graph.get(indexSource) , Dijkstra.graph.get(destSource));
        path.setText(s);
        distance.setText(Dijkstra.graph.get(destSource).getDV()+"");

    }

    public static Point2D getXY(double latitude, double longitude) {
        double mapWidth = map2.getFitWidth();
        double mapHeight =  map2.getFitHeight();

     //   System.out.println(mapWidth);
     //   System.out.println(mapHeight);



        double x = (longitude + 180) * ((mapWidth) / 360);
        double y = (1 - Math.log(Math.tan(Math.toRadians(latitude)) + 1 / Math.cos(Math.toRadians(latitude))) / Math.PI) * ((mapHeight) / 2);
    //    System.out.println("[lon: " + longitude + " lat: " + latitude + "]: X: " + x + " Y: " + y);
        Point2D point2D = new Point2D(x , y) ;
        return point2D ;

    }
    @FXML
    void srcOnAction(ActionEvent event) {
         indexSource = src.getItems().indexOf(src.getSelectionModel().getSelectedItem());

    }



    int indexSource , destSource ;
    @FXML
    void destOnAction(ActionEvent event) {
         destSource = dest.getItems().indexOf(dest.getSelectionModel().getSelectedItem());
    }
}