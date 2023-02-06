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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.imageio.ImageIO;
import java.awt.*;
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
    private TextArea path;

    @FXML
    private ComboBox<String> src;
    @FXML
    private ScrollPane displayImage;



static double imgWidth , imgHeight ;

static Pane pane ;
static ScrollPane scrollPane ;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane = displayImage ;

                Image img = null;
        Label  label = new Label() ;
        label.setFont(new Font(40));

             img = new Image("D:\\Algorithim\\Project_3_Dijikstra\\DijikstraGui\\src\\main\\resources\\com\\khanfar\\dijikstragui\\WGS84_Mercator_1.jpg");



        imgWidth = img.getWidth()  ;
        imgHeight =img.getHeight();
        imgWidth = 1200 ;
        imgHeight =700;

        Canvas canvas = new Canvas(imgWidth, imgHeight);
         pane = new Pane() ;
        pane.setMaxHeight(imgHeight);
        pane.setMaxWidth(imgWidth);
        pane.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(img, 0, 0 , imgWidth ,imgHeight );
        gc.setFill(Color.RED);
        pane.getChildren().add(label);


        for (Vertex vertex : Dijkstra.graph) {

            src.getItems().addAll(vertex.getName());
            dest.getItems().addAll(vertex.getName());



            Button button = new Button(vertex.getName());
            button.setOnAction(e -> {
                if (src.getValue()==null ) {
                    src.setValue(button.getText());
                }
               else if (dest.getValue()==null){
                   dest.setValue(button.getText());
                }


       Platform.runLater(new Runnable() {
           @Override
           public void run() {
               label.setText(button.getText());
           }
       });
                System.out.println(button.getText());


            });

            Point2D point2D = getXY(vertex.getX() , vertex.getY());
           // gc.fillOval(x, y, 15, 15);


            button.setLayoutX(point2D.getX());
            button.setLayoutY(point2D.getY());
            button.setStyle(
                    "-fx-background-radius: 5em; " +
                            "-fx-min-width: 7px; " +
                            "-fx-min-height: 7px; " +
                            "-fx-max-width: 7px; " +
                            "-fx-max-height: 7px;" +
                            "-fx-background-color: green"
            );
                 pane.getChildren().addAll(button) ;

        }



        for (Vertex v : Dijkstra.graph) {
            for (Map.Entry<String, Vertex> w : v.adjace.entrySet()) {
                System.out.println("tareq");
                Point2D point2D = getXY(v.getX() , v.getY());
                Point2D  point2D1 = getXY(w.getValue().getX() , w.getValue().getY());
                Line line =new Line(point2D.getX() , point2D.getY(), point2D1.getX() , point2D1.getY());

                pane.getChildren().addAll(line);

            }
        }
        displayImage.setContent(pane);







    }
    static Vertex  current  ;
   public static  LinkedList<Line> list = new LinkedList<>();
    public static String printPath(Vertex start, Vertex end) {
        current = null ;
        StringBuilder str= new StringBuilder();
        Stack <String> stack = new Stack<>() ;

        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                   for (Line n : list) {
                           pane.getChildren().removeAll(n);
                           n.setVisible(false);

                   }
                }
            });
             current = end ;
             Vertex prev = current.getPV() ;
            while (prev != null) {
               // str.append("   " + current.getName()  + "  And cost : " + current.getDV() + "----->" + prev.getName()  + "  And cost : " + prev.getDV() +"\n ##################################################\n");
              stack.push("Move from " + prev.getName() +" to " + current.getName() +"-- " +current.getDV() +"km");

                System.out.print("   " + current.getName()  + "  And cost : " + current.getDV() + "----->" + prev.getName()  + "  And cost : " + prev.getDV());
                Point2D point2D = getXY(current.getX() , current.getY());
                current = current.getPV();
                Point2D  point2D1 = getXY(prev.getX() , prev.getY());

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Line line =new Line(point2D.getX() , point2D.getY(), point2D1.getX() , point2D1.getY());
                        line.setFill(Color.RED);
                        line.setStroke(Color.RED);
                        line.setStrokeWidth(5);
                        Label  label = new Label("Tareq") ;
                        label.setFont(new Font(40));
                        list.add(line);
                        pane.getChildren().addAll(line , label);
                        scrollPane.setContent(pane);
                    }
                });
                current = prev;
                prev = current.getPV();

            }



        }
        catch (NullPointerException e ) {
            e.printStackTrace();
        }
        while (!stack.isEmpty()) {
            str.append(stack.pop()+"\n");
        }
        return str.toString() ;

    }

    @FXML
    void runOnAction(ActionEvent event) throws IOException {
        Dijkstra.graph.clear();
        try {
            Dijkstra.csvFile(startController.FILE_NAME) ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

        Dijkstra.startTable(startController.FILE_NAME);
        Dijkstra. adjForEachVertixs();

        Dijkstra.dijisktra(indexSource);

        String s =  printPath(Dijkstra.graph.get(indexSource) , Dijkstra.graph.get(destSource));
        path.setText(s);
        distance.setText(Dijkstra.graph.get(destSource).getDV()+"");

        src.setValue(null);
        dest.setValue(null);


    }

    public static Point2D getXY(double latitude, double longitude) {

     //   System.out.println(mapWidth);
     //   System.out.println(mapHeight);



        double longitudeRad = Math.toRadians(longitude);
        double latitudeRad = Math.toRadians(latitude);

        int x = (int) ((longitudeRad + Math.PI) * (imgWidth / (2 * Math.PI)));
        int y = (int) ((Math.PI - Math.log(Math.tan(latitudeRad) + 1 / Math.cos(latitudeRad))) * (imgHeight / (2 * Math.PI)));
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