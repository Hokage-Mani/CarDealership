package org.example;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SalesContractTest {

    @org.junit.jupiter.api.Test
    void getTotalPrice() {
        //Arrange
        Vehicle lindaCar = new Vehicle(6767, 2025, "Toyota", "GR Supra Premium", "Coupe",
                "Blue", 4985, 61500);
        SalesContract contract = new SalesContract("2024-05-15", "Linda", "email", lindaCar, true);
        //Act
        BigDecimal testValue = contract.getTotalPrice();
        //Assert
        assertEquals(new BigDecimal("65170.00"), testValue);
    }

    @Test
    public void getMonthlyPayment(){
        //Arrange
        Vehicle graceCar = new Vehicle(6767, 2025, "Toyota", "GR Supra Premium", "Coupe",
                "Blue", 4985, 61500);
        SalesContract contract2 = new SalesContract("2024-05-15", "Grace", "email", graceCar, false);
        //Act
        BigDecimal testValue2 = contract2.getMonthlyPayment();
        //Assert
        assertEquals(new BigDecimal("0"), testValue2);
    }
}