package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LeaseContract extends Contract{


    public LeaseContract(String date, String name, String email, Vehicle vehicleSold) {
        super(date, name, email, vehicleSold);
    }
    private BigDecimal getEndingValue(){
        double originalPrice = getVehicleSold().getPrice();
        return BigDecimal.valueOf(originalPrice).multiply(new BigDecimal("0.50"));
    }
    private BigDecimal getLeaseFee(){
        double originalPrice = getVehicleSold().getPrice();
        return BigDecimal.valueOf(originalPrice).multiply(new BigDecimal("0.07"));
    }

    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal total = getEndingValue().add(getLeaseFee());
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        //logic here
        return null;
    }
}
