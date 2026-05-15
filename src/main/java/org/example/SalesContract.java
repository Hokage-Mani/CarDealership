package org.example;

import java.math.BigDecimal;

public class SalesContract extends Contract{


    public SalesContract(String date, String name, String email, String vehicleSold) {
        super(date, name, email, vehicleSold);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        return null;
    }
}
