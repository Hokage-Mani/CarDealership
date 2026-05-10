package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DealershipFileManager {

    private static final String FILE_PATH = "src/main/resources/Inventory.csv";

    public static Dealership getDealership() {
        Dealership dealership = new Dealership(
                "O.D.S.T. CAR DEALERSHIP",
                "199 Chambers St, New York, NY 10007",
                "(212)-220-8000"
        );
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                if (input.trim().isEmpty()) {
                    continue;
                }
                String[] csvRow = input.split("\\|");

                int vin = Integer.parseInt(csvRow[0].trim());
                int year = Integer.parseInt(csvRow[1].trim());
                String make = csvRow[2].trim();
                String model = csvRow[3].trim();
                String vehicleType = csvRow[4].trim();
                String color = csvRow[5].trim();
                int odometer = Integer.parseInt(csvRow[6].trim());
                double price = Double.parseDouble(csvRow[7].trim());

                Vehicle vh = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

                dealership.addVehicle(vh);
            }

        } catch (IOException e) {
            System.out.println("Error reading the inventory file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing vehicle data: " + e.getMessage());
        }
        return dealership;
    }
    public static void saveDealership(Dealership dealership) {

        try (FileWriter fw = new FileWriter(FILE_PATH);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            List<Vehicle> inventory = dealership.getAllVehicles();
            for (Vehicle vh : inventory) {
                String lineToWrite = vh.getVin() + "|" +
                        vh.getYear() + "|" +
                        vh.getMake() + "|" +
                        vh.getModel() + "|" +
                        vh.getVehicleType() + "|" +
                        vh.getColor() + "|" +
                        vh.getOdometer() + "|" +
                        vh.getPrice();

                out.println(lineToWrite);
            }

        } catch (IOException e) {
            System.out.println("Error completely overwriting the inventory file: " + e.getMessage());
        }
    }
}