package org.example;

import java.math.BigDecimal;

public class SalesContract extends Contract{
    private boolean isFinanced;

    public SalesContract(String date, String name, String email, String vehicleSold) {
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

        return null;
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        return null;
    }
}
