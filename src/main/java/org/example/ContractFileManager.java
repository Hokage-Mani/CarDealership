package org.example;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;

public class ContractFileManager {
    private static final String FILE_NAME = "contracts.csv";

    public void saveContract(Contract contract){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))){
            Vehicle v = contract.getVehicleSold();

            String vehicleInfo =String.format("%d|%d|%s|%s|%s|%s|%d|%d|%.2f",
                    v.getVin(), v.getYear(), v.getMake(), v.getModel(), v.getVehicleType(), v.getColor(),
                    v.getOdometer(), v.getPrice());
            String baseInfo = String.format("%s|%s|%s|%s",
                    contract.getDate(), contract.getName(), contract.getEmail(), vehicleInfo);
            //Instanceof to determine weather the contract is Lease or Sale
            if(contract instanceof SalesContract){
                SalesContract salesContract = (SalesContract) contract;
                //Use of Ternary operator "?" and ":" for the shortcut of an if statement
                String financeOption = salesContract.isFinanced() ? "Yes":"No";
                String line = String.format("SALE|%s|%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                        baseInfo, salesContract.getSalesTaxAmount(), salesContract.getRecordingFee(),
                        salesContract.getProcessingFee(), salesContract.getTotalPrice(), financeOption,
                        salesContract.getMonthlyPayment());
                writer.write(line);
                writer.newLine();
            }
        }catch (IOException e){
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }
}
