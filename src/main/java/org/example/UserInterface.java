package org.example;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class UserInterface {
    private Dealership dealership;
    private Scanner scanner;

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.display();
    }
    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.dealership = DealershipFileManager.getDealership();
    }
    public void display() {
        printHeader();
        boolean running = true;

        while (running) {
            System.out.println("====================================================\n" +
                    "        O.D.S.T AUTO - INVENTORY SYSTEM\n" +
                    "====================================================");
            System.out.println("1. Find vehicles within a price range");
            System.out.println("2. Find vehicles by Make/Model");
            System.out.println("3. Find vehicles by year range");
            System.out.println("4. Find vehicles by color");
            System.out.println("5. Find vehicles by mileage range");
            System.out.println("6. Find vehicles by type");
            System.out.println("7. List ALL vehicles");
            System.out.println("8. Enter Inventory Manager");
            System.out.println("99. Exit");
            System.out.print("Please select an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    processGetByPriceRequest();
                    break;
                case "2":
                    processGetByMakeModelRequest();
                    break;
                case "3":
                    processGetByYearRequest();
                    break;
                case "4":
                    processGetByColorRequest();
                    break;
                case "5":
                    processGetByMileageRequest();
                    break;
                case "6":
                    processGetByVehicleTypeRequest();
                    break;
                case "7":
                    processGetAllVehiclesRequest();
                    break;
                case "8":
                    openInventoryManager();
                    break;
                case "99":
                    System.out.println("\nExiting the O.D.S.T. Dealership. Goodbye!\n");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid option. Please try again.\n");
                    break;
            }
        }
        scanner.close();
    }
    public void processGetByPriceRequest() {
        System.out.println("\n[ Find Vehicles by Price Range ]\n");
        try {
            System.out.print("Enter minimum price: $");
            double minPrice = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter maximum price: $");
            double maxPrice = Double.parseDouble(scanner.nextLine().trim());
            List<Vehicle> matches = dealership.getVehiclesByPrice(minPrice, maxPrice);
            printVehicleList(matches, String.format("No vehicles found within the range of $%.2f - $%.2f", minPrice, maxPrice));
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please enter valid numbers.");
        }
    }
    public void processGetByMakeModelRequest() {
        System.out.println("\n[ Find Vehicles by Make or Model ]\n");
        printAvailableOptions("MAKE_MODEL");
        System.out.print("Enter the Make or Model you are looking for: ");
        String makeModelSearch = scanner.nextLine().trim();
        List<Vehicle> matches = dealership.getVehiclesByMakeModel(makeModelSearch, makeModelSearch);
        printVehicleList(matches, "No vehicles found matching Make/Model: \"" + makeModelSearch + "\"");
    }
    public void processGetByYearRequest() {
        System.out.println("\n[ Find Vehicles by Year Range ]\n");
        try {
            System.out.print("Enter minimum year: ");
            int minYear = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter maximum year: ");
            int maxYear = Integer.parseInt(scanner.nextLine().trim());
            List<Vehicle> matches = dealership.getVehiclesByYear(minYear, maxYear);
            printVehicleList(matches, String.format("No vehicles found within the range of %d - %d", minYear, maxYear));
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please enter valid whole numbers.");
        }
    }
    public void processGetByColorRequest() {
        System.out.println("\n[ Find Vehicles by Color ]\n");
        printAvailableOptions("COLOR");
        System.out.print("Enter the color you are looking for: ");
        String colorSearch = scanner.nextLine().trim();
        List<Vehicle> matches = dealership.getVehiclesByColor(colorSearch);
        printVehicleList(matches, "No vehicles found matching Color: \"" + colorSearch + "\"");
    }
    public void processGetByMileageRequest() {
        System.out.println("\n[ Find Vehicles by Mileage Range ]\n");
        try {
            System.out.print("Enter minimum mileage: ");
            int minMileage = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter maximum mileage: ");
            int maxMileage = Integer.parseInt(scanner.nextLine().trim());

            List<Vehicle> matches = dealership.getVehiclesByMileage(minMileage, maxMileage);
            printVehicleList(matches, String.format("No vehicles found within the range of %d - %d", minMileage, maxMileage));
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please enter valid whole numbers.");
        }
    }
    public void processGetByVehicleTypeRequest() {
        System.out.println("\n[ Find Vehicles by Type ]\n");
        printAvailableOptions("TYPE");
        System.out.print("Enter vehicle type (e.g., Sedan, Truck, SUV, Coupe, Van): ");
        String typeSearch = scanner.nextLine().trim();
        List<Vehicle> matches = dealership.getVehiclesByType(typeSearch);
        printVehicleList(matches, "No vehicles found matching Type: \"" + typeSearch + "\"");
    }
    public void processGetAllVehiclesRequest() {
        System.out.println("\n[ Listing All Vehicles... ]\n");
        List<Vehicle> matches = dealership.getAllVehicles();
        printVehicleList(matches, "There are currently no vehicles in the inventory.");
    }

    public void processAddVehicleRequest() {
        System.out.println("\n[ Adding Vehicle. . . ]\n");
        try {
            int vin = getValidVin(scanner, dealership.getAllVehicles());
            System.out.print("Enter year of the vehicle: ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter make of the vehicle: ");
            String make = scanner.nextLine().trim();
            System.out.print("Enter model of the vehicle: ");
            String model = scanner.nextLine().trim();
            System.out.print("Enter vehicle type (Sedan, Truck, SUV, Van, Coupe): ");
            String vtType = scanner.nextLine().trim();
            System.out.print("Enter color of the vehicle: ");
            String color = scanner.nextLine().trim();
            System.out.print("Enter total mileage (Odometer): ");
            int odometer = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("\n--- What is the vehicle price? ---");
            double price = getValidAmount(scanner);
            System.out.print("Confirm adding this vehicle? (Y/N): ");
            String confirmAdd = scanner.nextLine().trim();
            if (confirmAdd.equalsIgnoreCase("y")) {
                Vehicle newVehicle = new Vehicle(vin, year, make, model, vtType, color, odometer, price);
                dealership.addVehicle(newVehicle);
                DealershipFileManager.saveDealership(dealership);

                System.out.println("Vehicle added successfully!");
            } else {
                System.out.println("\nAction canceled. The vehicle was not saved.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please enter valid whole numbers.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    public void processRemoveVehicleRequest() {
        System.out.println("\n[ Remove a Vehicle ]\n");
        try {
            List<Vehicle> inventory = dealership.getAllVehicles();
            if (inventory == null || inventory.isEmpty()) {
                System.out.println("The inventory is completely empty. Nothing to remove.");
                return;
            }
            printVehicleList(inventory, "Inventory empty.");
            System.out.println("\nHow would you like to find the vehicle to remove?");
            System.out.println("1. By VIN (Recommended - Exact Match)");
            System.out.println("2. By Make or Model");
            System.out.print("Select an option: ");
            String removeOption = scanner.nextLine().trim();
            Vehicle targetVehicle = null;
            if (removeOption.equals("1")) {
                System.out.print("Enter the exact 4-digit VIN of the vehicle to remove: ");
                int targetVin = Integer.parseInt(scanner.nextLine().trim());
                for (Vehicle v : inventory) {
                    if (v.getVin() == targetVin) {
                        targetVehicle = v;
                        break;
                    }
                }
            } else if (removeOption.equals("2")) {
                System.out.print("Enter the Make or Model to remove: ");
                String targetName = scanner.nextLine().trim();
                for (Vehicle v : inventory) {
                    if (v.getMake().equalsIgnoreCase(targetName) || v.getModel().equalsIgnoreCase(targetName)) {
                        targetVehicle = v;
                        break;
                    }
                }
            } else {
                System.out.println("Invalid option selected.");
                return;
            }
            if (targetVehicle != null) {
                System.out.printf("\nFound: %d %s %s (VIN: %d). Are you SURE you want to delete this? (Y/N): ",
                        targetVehicle.getYear(), targetVehicle.getMake(), targetVehicle.getModel(), targetVehicle.getVin());
                String confirmDelete = scanner.nextLine().trim();
                if (confirmDelete.equalsIgnoreCase("y")) {
                    dealership.removeVehicle(targetVehicle);
                    DealershipFileManager.saveDealership(dealership);

                    System.out.println("Vehicle successfully removed from inventory!");
                } else {
                    System.out.println("Removal canceled. The vehicle is safe.");
                }
            } else {
                System.out.println("\nNo matching vehicle was found in the system.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric input provided.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    private void openInventoryManager() {
        boolean inInventoryMenu = true;
        while (inInventoryMenu) {
            System.out.println("\n=== INVENTORY MANAGEMENT ===");
            System.out.println("A) Add a vehicle");
            System.out.println("X) Remove a vehicle");
            System.out.println("S) Sell a vehicle");
            System.out.println("L) Lease a vehicle");
            System.out.println("C) View all Contracts");
            System.out.println("H) Return home");
            System.out.print("Select an option: ");
            String menuInput = scanner.nextLine().trim().toUpperCase();
            switch (menuInput) {
                case "A":
                    processAddVehicleRequest();
                    break;
                case "X":
                    processRemoveVehicleRequest();
                    break;
                case "S":
                    //Selling a vehicle
                    Vehicle saleVehicle = null;
                    System.out.println("\n--- Sale Processing ---");
                    processGetAllVehiclesRequest();
                    System.out.println("Enter the 4-digit VIN of vehicle to sell: ");
                    int saleVIN = Integer.parseInt(scanner.nextLine().trim());
                    for(Vehicle v : dealership.getAllVehicles()){
                        if(v.getVin() == saleVIN){
                            saleVehicle = v;
                            break;
                        }
                    }
                    if(saleVehicle == null){
                        System.out.println("No vehicle matches with VIN: " + saleVIN);
                        break;
                    }
                    System.out.print("Enter customer name: ");
                    String customerName = scanner.nextLine().trim();
                    System.out.print("Enter customer email: ");
                    String customerEmail = scanner.nextLine().trim();
                    System.out.println("Confirmed to sell: " + saleVehicle.getYear() + " " +
                            saleVehicle.getMake() + " " + saleVehicle.getModel());
                    System.out.println("Contract price: $" + saleVehicle.getPrice());
                    System.out.println("Will this purchase be financed? (y/n)");
                    String financeChoice = scanner.nextLine().trim();
                    boolean isFinanced = financeChoice.equalsIgnoreCase("Y");
                    String date = java.time.LocalDate.now().toString();
                    SalesContract salesContract = new SalesContract(date, customerName, customerEmail, saleVehicle, isFinanced);
                    // Saving the contract into the new CSV file
                    ContractFileManager contractFileManager = new ContractFileManager();
                    contractFileManager.saveContract(salesContract);
                    // Making sure to remove the vehicle out of inventory!
                    dealership.removeVehicle(saleVehicle);
                    DealershipFileManager.saveDealership(dealership);
                    System.out.println("\nSale Processed! Contract saved. Inventory updated.");
                    System.out.println("Press ENTER to return...");
                    scanner.nextLine();
                    break;
                case "L":
                    //Leasing a vehicle
                    Vehicle leaseVehicle = null;
                    System.out.println("\n--- Lease Processing ---");
                    processGetAllVehiclesRequest();
                    System.out.println("Enter the 4-digit VIN of vehicle to sell: ");
                    int leaseVIN = Integer.parseInt(scanner.nextLine().trim());
                    for(Vehicle v : dealership.getAllVehicles()){
                        if(v.getVin() == leaseVIN){
                            leaseVehicle = v;
                            break;
                        }
                    }
                    if(leaseVehicle == null){
                        System.out.println("No vehicle matches with VIN: " + leaseVIN);
                        break;
                    }
                    int currentYear = java.time.LocalDate.now().getYear();
                    if(currentYear - leaseVehicle.getYear() > 3){
                        System.out.println("Vehicle ineligible for lease. Must be under 3 years old.");
                        break;
                    }
                    System.out.println("Enter customer name: ");
                    String leaseName = scanner.nextLine().trim();
                    System.out.println("Enter customer email: ");
                    String leaseEmail = scanner.nextLine().trim();
                    System.out.println("\n Confirmed to lease:" + leaseVehicle.getYear() + " " +
                            leaseVehicle.getMake() + " " + leaseVehicle.getModel());
                    String leaseDate = java.time.LocalDate.now().toString();
                    LeaseContract leaseContract = new LeaseContract(leaseDate, leaseName, leaseEmail, leaseVehicle);
                    ContractFileManager contractFileManagerL = new ContractFileManager();
                    contractFileManagerL.saveContract(leaseContract);
                    dealership.removeVehicle(leaseVehicle);
                    DealershipFileManager.saveDealership(dealership);
                    System.out.println("\n Lease processed! Contract saved. Inventory updated.");
                    System.out.println("Press ENTER to return....");
                    scanner.nextLine();
                    break;
                case "C":
                    System.out.println("\n--- Listing All Contracts ---");
                    ContractFileManager displayHelper = new ContractFileManager();
                    displayHelper.displayAllContracts();
                    System.out.println("Press ETNER to return...");
                    scanner.nextLine();
                    break;
                case "H":
                    System.out.println("\nReturning to Main Menu...");
                    inInventoryMenu = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
    public void printHeader() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                                              ║");
        System.out.println("║            O.D.S.T. CAR DEALERSHIP           ║");
        System.out.println("║                                              ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("199 Chambers St, New York, NY 10007 ");
        System.out.println("(212)-220-8000");
    }
    //Researched built-in TreeSet automatically sorts alphabetically and ignores duplicates.
    private void printAvailableOptions(String category) {
        try {
            List<Vehicle> vehicleList = dealership.getAllVehicles();
            if (vehicleList == null || vehicleList.isEmpty()) {
                return;
            }
            Set<String> uniqueOptions = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

            for (Vehicle v : vehicleList) {
                switch (category.toUpperCase()) {
                    case "COLOR":
                        uniqueOptions.add(v.getColor().trim());
                        break;
                    case "MAKE_MODEL":
                        uniqueOptions.add(v.getMake().trim());
                        uniqueOptions.add(v.getModel().trim());
                        break;
                    case "TYPE":
                        uniqueOptions.add(v.getVehicleType().trim());
                        break;
                }
            }
            String displayHeader = category.replace("_", "/");
            System.out.println("--- Available " + displayHeader + "s ---");
            for (String option : uniqueOptions) {
                System.out.println(" • " + option);
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error loading available options: " + e.getMessage());
        }
    }
    private void printVehicleList(List<Vehicle> vehicles, String emptyMessage) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        System.out.println("+------------+------+-----------------+---------------------------+------------+----------------------+------------+---------------+");
        System.out.printf("| %-10s | %-4s | %-15s | %-25s | %-10s | %-20s | %-10s | %-13s |%n",
                "VIN", "Year", "Make", "Model", "Type", "Color", "Mileage", "Price");
        System.out.println("+------------+------+-----------------+---------------------------+------------+----------------------+------------+---------------+");
        for (Vehicle vhs : vehicles) {
            printVehicle(vhs);
        }
        System.out.println("+------------+------+-----------------+---------------------------+------------+----------------------+------------+---------------+");
    }
    private void printVehicle(Vehicle vehicle) {
        System.out.printf("| %-10s | %-4d | %-15s | %-25s | %-10s | %-20s | %-10d | $%-12.2f |%n",
                vehicle.getVin(),
                vehicle.getYear(),
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getVehicleType(),
                vehicle.getColor(),
                vehicle.getOdometer(),
                vehicle.getPrice()
        );
    }
    private static double getValidAmount(Scanner scanner) {
        while (true) {
            System.out.print("Enter amount: $");
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("ERROR: Invalid number. Please enter digits only (e.g., 50.00).");
            }
        }
    }
    private static int getValidVin(Scanner scanner, List<Vehicle> vehicleList) {
        while (true) {
            System.out.print("What is the 4-digit VIN# for the vehicle? ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{4}") && Integer.parseInt(input) >= 1000) {
                int proposedVin = Integer.parseInt(input);
                boolean isDuplicate = false;

                if (vehicleList != null) {
                    for (Vehicle v : vehicleList) {
                        if (v.getVin() == proposedVin) {
                            isDuplicate = true;
                            break;
                        }
                    }
                }
                if (isDuplicate) {
                    System.out.println("ERROR: That VIN already exists in the system. Please enter a unique VIN.");
                } else {
                    return proposedVin;
                }
            } else {
                System.out.println("ERROR: Invalid VIN. You must enter exactly 4 digits and it cannot start with 0 (e.g., 1054).");
            }
        }
    }
}