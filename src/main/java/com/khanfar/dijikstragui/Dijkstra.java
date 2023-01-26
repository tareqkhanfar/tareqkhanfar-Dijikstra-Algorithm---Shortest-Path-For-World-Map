package com.khanfar.dijikstragui;



import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Dijkstra {

   static  Map<VertexFromTo, Integer>distanceFromTo = new HashMap<>();
   static LinkedList <Vertex> graph = new LinkedList<>();


    public static void csvFile () throws IOException, InvalidFormatException {
        XSSFWorkbook work  = new XSSFWorkbook(new FileInputStream("f.xlsx"));
        XSSFSheet sheet = work.getSheetAt(0);
        XSSFRow row = null ;
        int i = 1;
        while ((row = sheet.getRow(i)) != null) {
            String city = row.getCell(0).getStringCellValue();
            double x= row.getCell(1).getNumericCellValue();
            double y = row.getCell(2).getNumericCellValue();
            Vertex v = new Vertex(city , null , Integer.MAX_VALUE , false , x , y) ;
           graph.add(v) ;


            i++;

        }

    }









    public static  void startTable () {

        Random r = new Random();
        int low = 0;
        int high = 200;

        for (int i = 0 ; i < 1600 ; i++) {
            int rand1 = (r.nextInt(high-low) + low )% graph.size();
            int rand2 = (r.nextInt(high-low) + low +1) % graph.size();
            int rand3 = (r.nextInt(high-low) + low+1);

            if (rand1==rand2) {
                i-- ;
                continue;
            }

            graph.get(rand1).adjace.put(graph.get(rand2).getName() , graph.get(rand2));
            graph.get(rand2).adjace.put(graph.get(rand1).getName() , graph.get(rand1));


            distanceFromTo.put(new VertexFromTo(graph.get(rand1) , graph.get(rand2)) , rand3);
            distanceFromTo.put(new VertexFromTo(graph.get(rand2) , graph.get(rand1)) , rand3);



        }
        System.out.println(distanceFromTo);


    }
    public static  void adjForEachVertixs () {
        for (Vertex v : graph) {
            System.out.println(v.getName());
            for(Map.Entry<String, Vertex> u : v.adjace.entrySet() ){
                Vertex w = u.getValue() ;

                System.out.print(w.getName()+"----");
            }
            System.out.println("\n\n");
        }
    }

    public static void dijisktra(int index) {

        graph.get(index).setDV(0); // this a start vertix , change the distance to zero
        PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();

        pq.add(graph.get(index));

        while (!pq.isEmpty()) {
         Vertex v = pq.poll() ;
         if(v==null) {
             break;
         }
         v.setKnown(true);

         for(Map.Entry<String, Vertex> u : v.adjace.entrySet() ){
             Vertex w = u.getValue() ;
             if (!w.isKnown()) {
                 if (v.getDV() + Cost(v, w) < w.getDV()) {
                     w.setDV(v.getDV() + Cost(v, w));
                     w.setPV(v);
                     pq.add(w);
                 }
             }
         }

        }



    }





    private static int Cost(Vertex v, Vertex w) {
      return distanceFromTo.get(new VertexFromTo(v ,w));
    }



    static class VertexFromTo {
            Vertex x;

            Vertex y;



        public VertexFromTo(Vertex v, Vertex w ) {
            this.x = v;
            this.y = w;

        }



        @Override
            public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((x == null) ? 0 : x.hashCode());
                result = prime * result + ((y == null) ? 0 : y.hashCode());
                return result;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj)
                    return true;
                if (obj == null)
                    return false;
                if (getClass() != obj.getClass())
                    return false;
                VertexFromTo other = (VertexFromTo) obj;
                if (x == null) {
                    if (other.x != null)
                        return false;
                } else if (!x.equals(other.x))
                    return false;
                if (y == null) {
                    if (other.y != null)
                        return false;
                } else if (!y.equals(other.y))
                    return false;
                return true;
            }

        @Override
        public String toString() {
            return "VertexFromTo{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }


}
