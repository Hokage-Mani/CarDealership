package org.example;

import java.math.BigDecimal;

public abstract class Contract {
    private String date;
    private String name;
    private String email;
    private String vehicleSold;
    private BigDecimal totalPrice;
    private BigDecimal monthlyPayment;

    public Contract(String date, String name, String email, String vehicleSold) {
        this.date = date;
        this.name = name;
        this.email = email;
        this.vehicleSold = vehicleSold;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getVehicleSold() {
        return vehicleSold;
    }
    public void setVehicleSold(String vehicleSold) {
        this.vehicleSold = vehicleSold;
    }
    public abstract BigDecimal getTotalPrice();

    public abstract BigDecimal getMonthlyPayment();

}
