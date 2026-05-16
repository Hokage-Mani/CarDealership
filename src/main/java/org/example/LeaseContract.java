package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LeaseContract extends Contract{


    public LeaseContract(String date, String name, String email, Vehicle vehicleSold) {
        super(date, name, email, vehicleSold);
    }
    public BigDecimal getEndingValue(){
        double originalPrice = getVehicleSold().getPrice();
        return BigDecimal.valueOf(originalPrice).multiply(new BigDecimal("0.50"));
    }
    public BigDecimal getLeaseFee(){
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
        double originalPrice = getVehicleSold().getPrice();
        double endingValue = getEndingValue().doubleValue();
        double leasePrincipal = originalPrice - endingValue;
        double rate = 0.04/12; //4% of annual rate divided by 12 months for 36 months
        int months = 36;
        double monthlyPay = leasePrincipal * (rate / (1 - Math.pow(1 + rate, -months)));
        return BigDecimal.valueOf(monthlyPay).setScale(2, RoundingMode.HALF_UP);
    }
}
