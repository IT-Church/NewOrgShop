package com.paramonod.kikos.pack;

/**
 * Created by Vadim on 10.09.2016.
 */
public class Adress {
    private String street;
    private int house;
    private double CoordX;
    private double CoordY;
    public Adress(){
        this.street = "Novslobodskaya";
        this.house = 38;
        this.CoordX = 0;
        this.CoordY = 0;
    }

    public double getCoordX(){return this.CoordX;}

    public double getCoordY(){return this.CoordY;}

    public int getHouse() {
        return this.house;
    }

    public String getStreet() {
        return this.street;
    }

    public void setCoordX(double CoordX) {this.CoordX = CoordX;}

    public void setCoordY(double CoordY) {this.CoordY = CoordY;}


    public void setHouse(int house) {
        this.house = house;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return street+ Integer.toString(house);
    }
}
