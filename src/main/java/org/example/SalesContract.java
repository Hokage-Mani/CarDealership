package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SalesContract extends Contract{
    private boolean isFinanced;

    public SalesContract(String date, String name, String email, Vehicle vehicleSold, boolean b) {
        super(date, name, email, vehicleSold);
        this.isFinanced = isFinanced;
    }
    public boolean isFinanced() {
        return isFinanced;
    }
    public void setFinanced(boolean financed) {
        isFinanced = financed;
    }

    @Override
    public BigDecimal getTotalPrice() {

        double basePriceDouble = getVehicleSold().getPrice();
        BigDecimal basePrice = BigDecimal.valueOf(basePriceDouble);

        BigDecimal salesTax = basePrice.multiply(new BigDecimal("0.05"));
        BigDecimal recordingFee = new BigDecimal("100.00");
        BigDecimal processingFee;
        if(basePriceDouble < 10000){
            processingFee = new BigDecimal("295.00");
        }else processingFee = new BigDecimal("495.00");
        return basePrice.add(salesTax).add(recordingFee).add(processingFee).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        if (!isFinanced) {
            //
            return BigDecimal.ZERO;
        }
        double basePriceDouble = getVehicleSold().getPrice();
        double loanPrincipal = getTotalPrice().doubleValue();
        double rate;
        int months;

        if (basePriceDouble >= 10000.00) {
            rate = 0.0425 / 12; //4.25% divided by 12 months 48 month term
            months = 48;
        } else
            rate = 0.0525 / 12; //5.25% divided by 12 month for 24 month term
        months = 24;
        double monthlyPayment = loanPrincipal * (rate / (1 - Math.pow(1 + rate, -months )));
        //rounding up 2 places for the sake of dealing with currency
        return BigDecimal.valueOf(monthlyPayment).setScale(2, RoundingMode.HALF_UP);
    }
    //These methods are to help branch the ContractFileManager inside the class
    public BigDecimal getSalesTaxAmount() {
        double price = getVehicleSold().getPrice();
        return BigDecimal.valueOf(price).multiply(new BigDecimal("0.05"));
    }
    public BigDecimal getRecordingFee() {
        return new BigDecimal("100.00");
    }
    public BigDecimal getProcessingFee() {
        if (getVehicleSold().getPrice() < 10000.00) {
            return new BigDecimal("295.00");
        } else {
            return new BigDecimal("495.00");
        }
    }
}
