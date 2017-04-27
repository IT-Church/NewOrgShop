package com.paramonod.kikos.pack;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

public class ShopInterface {
    private String Name;
    private String Description;
    private String street;
    private int house;
    private double CoordX;
    private double CoordY;
    private String PictureName;
    //private ArrayList<Item> products;
    //private ArrayList<Sale> sales;
    //private Certificate certificate;
    //private Image photo;

    public ShopInterface() {
    }
    public ShopInterface(String Name, String Description, String street, String house, String CoordX, String CoordY, String PictureName){
        this.Name = Name;
        this. Description = Description;
        this.street = street;
        this.house = Integer.parseInt(house);
        this.CoordX = Double.parseDouble(CoordX);
        this.CoordY = Double.parseDouble(CoordY);
        this.PictureName = PictureName;
    }

   /* private void pop(ArrayList<?> arrayList, Object whatToDelete) {
        arrayList.remove(whatToDelete);
    }

    public void deleteSale(Sale o) {
        this.pop(sales, o);
        //TODO : menu.update();
    }

    public void deleteItem(Item i) {
        this.pop(products, i);
    }

    public void addSale(Sale o) {
        sales.add(o);
    }

    public void addItem(Item i) {
        products.add(i);
    }

    @Override
    public String toString() {
        return name+adress;
    }

    public Adress getAdress() {
        return Adress;
    }

    /*public ArrayList<Item> getProducts() {
        return products;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public Certificate getCertificate() {
        return certificate;

    }

    public Image getPhoto() {
        return photo;
    }*/

    public String getName() {
        return Name;
    }
    public String getPictureName() {
        return PictureName;
    }
    public String getDescription() {
        return Description;
    }

    /*public void setAdress(Adress adress) {
        this.Adress = adress;
    }

    /*public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }*/
    public void setName(String name) {
        this.Name = name;
    }
    public void setPictureName(String PictureName) {
        this.PictureName = PictureName;
    }
    public void setDescription(String description) {
        this.Description = description;
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

   /* public void setPhoto(Image photo) {
        this.photo = photo;
    }
    public Item searchItemByName(String name){
       ArrayList<Item> p = getProducts();
        for(Item i:p){
            if (i.getName().equals( name)){
                return i;
            }
        }
        return null;
    }*/
}
