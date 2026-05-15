package org.example;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LeaseContractTest {

    @Test
    void getTotalPrice() {
        //Arrange
        Vehicle lindaCar = new Vehicle(6767, 2025, "Toyota", "GR Supra Premium", "Coupe",
                "Blue", 4985, 61500);
        LeaseContract contract = new LeaseContract("2024-05-15", "Linda", "email@email.com", lindaCar);
        //Act
        BigDecimal valueTest = contract.getTotalPrice();
        //Assert
        assertEquals(new BigDecimal("35055.00"), valueTest);
    }

    @Test
    void getMonthlyPayment() {
        //Arrange
        Vehicle graceCar = new Vehicle(6767, 2025, "Toyota", "GR Supra Premium", "Coupe", "Blue", 4985, 61500);
        LeaseContract contract = new LeaseContract("2024-05-15", "Linda", "email@email.com", graceCar);
        //Act
        BigDecimal valueTest2 = contract.getMonthlyPayment();
        //Assert
        assertEquals(new BigDecimal("907.65"), valueTest2);
    }
}