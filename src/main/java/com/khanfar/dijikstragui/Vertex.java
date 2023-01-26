package com.khanfar.dijikstragui;

import java.util.HashMap;
import java.util.LinkedList;

public class Vertex implements Comparable<Vertex> {

    private String name ;
    private  Vertex PV ;
    private  int  DV ;

    private double x;
    private double y ;


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    LinkedList <Vertex> adjacent = new LinkedList<>();
    HashMap<String , Vertex> adjace = new HashMap();

    private  boolean known = false;



    public Vertex(String name, Vertex PV, int DV, boolean known , double x , double y) {
        this.name = name;
        this.PV = PV;
        this.DV = DV;
        this.known = known;
        this.x = x ;
        this.y = y ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vertex getPV() {
        return PV;
    }

    public void setPV(Vertex PV) {
        this.PV = PV;
    }

    public int getDV() {
        return DV;
    }

    public void setDV(int DV) {
        this.DV = DV;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    @Override
    public int compareTo(Vertex o) {
        return this.getDV() - o.getDV();
    }


    @Override
    public String toString() {
        return "Vertex{" +
                "name='" + name + '\'' +
                '}';
    }
}

