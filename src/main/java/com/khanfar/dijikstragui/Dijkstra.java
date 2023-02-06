package com.khanfar.dijikstragui;



import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class Dijkstra {

   static  Map<VertexFromTo, Integer>distanceFromTo = new HashMap<>();
   static LinkedList <Vertex> graph = new LinkedList<>();

static int nVertix =0;
    static int nEdges =0 ;
    public static void csvFile (String fileName) throws IOException, InvalidFormatException {
        XSSFWorkbook work  = new XSSFWorkbook(new FileInputStream(fileName));
        XSSFSheet sheet = work.getSheetAt(0);
        XSSFRow row = null ;

        row = sheet.getRow(0) ;
        nVertix = (int) row.getCell(0).getNumericCellValue();
        nEdges = (int) row.getCell(1).getNumericCellValue();


        int i = 1;
        while ((row = sheet.getRow(i)) != null) {
            if (i == nVertix) {
                break;
            }
            String city = row.getCell(0).getStringCellValue();
            double x= row.getCell(1).getNumericCellValue();
            double y = row.getCell(2).getNumericCellValue();
            Vertex v = new Vertex(city , null , Integer.MAX_VALUE , false , x , y) ;
           graph.add(v) ;

            i++;

        }

    }









    public static  void startTable (String fileName) throws IOException {
        XSSFWorkbook work  = new XSSFWorkbook(new FileInputStream(fileName));
        XSSFSheet sheet = work.getSheetAt(0);
        XSSFRow row = null ;
        int i = nVertix+2;
        System.out.println("#######" + i);
        while ((row = sheet.getRow(i)) != null) {
            String city1 = row.getCell(0).getStringCellValue();
            String city2 = row.getCell(1).getStringCellValue();
             Vertex v1 = searchVertexByName(city1) ;
            Vertex v2 = searchVertexByName(city2) ;
           v1.adjace.put(v2.getName() , v2);
            //   graph.get(rand2).adjace.put(graph.get(rand1).getName() , graph.get(rand1));

            double lat1 = v1.getX();
            double lat2 = v2.getX();
            double lon1 = v1.getY();
            double lon2 = v2.getY();
            distanceFromTo.put(new VertexFromTo(v1 ,v2) , distance(lat1, lat2, lon1, lon2));

            i++;

        }


    }

    private static Vertex searchVertexByName(String city1) {
        for (Vertex v : graph) {
            if (v.getName().equals(city1)) {
                return v ;
            }
        }
        return null ;
    }

    public static void generateEdges (String fileName) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Edges");
        XSSFRow row;
        Map<String, Object[]> edges
                = new TreeMap<String, Object[]>();
        Random r = new Random();
        int low = 0;
        int high = 200;

        int k = 1 ;
        for (int i = 0 ; i < 600 ; i++) {
            int rand1 = (r.nextInt(high - low) + low) % graph.size();
            int rand2 = (r.nextInt(high - low) + low + 1) % graph.size();
            if (rand1 == rand2) {
                i--;
                continue;
            }
            edges.put(k+"", new Object[] {graph.get(rand1).getName(), graph.get(rand2).getName()});
            k++;

        }



        int rowid = 0;
        row = spreadsheet.createRow(rowid++);
        Cell cell = row.createCell(0);
        cell.setCellValue(graph.size());
         cell = row.createCell(1);
        cell.setCellValue(edges.size());

        for (Vertex vertex : graph) {
            row = spreadsheet.createRow(rowid++);
            cell = row.createCell(0);
            cell.setCellValue(vertex.getName());

            cell = row.createCell(1);
            cell.setCellValue(vertex.getX());

            cell = row.createCell(2);
            cell.setCellValue(vertex.getY());

        }



        // writing the data into the sheets...
        Set<String> keyid = edges.keySet();

        for (String key : keyid) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = edges.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                 cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }
        FileOutputStream out = new FileOutputStream(
                new File(fileName));

        workbook.write(out);
        out.flush();
        out.close();


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
    public static int distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return (int) (c * r);
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
