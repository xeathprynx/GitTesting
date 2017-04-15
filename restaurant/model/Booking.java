/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model;

/**
 *
 * @author Prakas
 */
public class Booking {
   private String day;
   private int diners;
   private int id;
   private String name;
   private String phone;

    public Booking(String day, int diners, int id, String name, String phone) {
        this.day = day;
        this.diners = diners;
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDiners() {
        return diners;
    }

    public void setDiners(int diners) {
        this.diners = diners;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Booking{" + "day=" + day + ", diners=" + diners + ", id=" + id + ", name=" + name + ", phone=" + phone + "}\n";
    }
    
   
   
}
