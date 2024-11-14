package controller;

import model.Medicine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MedicineController {
    private final String medicineFilePath = "assets/medicine.csv"; // Path to the CSV file
    private Map<String, Medicine> medicineMap; // HashMap to store medicine data

    // Constructor to initialize the medicine map and load data
    public MedicineController() {
        medicineMap = new HashMap<>();
        loadMedicines(); // Load medicines from the CSV file
    }

    // Method to load medicines from the CSV file into the HashMap
    private void loadMedicines() {
        try (BufferedReader br = new BufferedReader(new FileReader(medicineFilePath))) {
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    String name = values[0].trim();
                    int currentStock = Integer.parseInt(values[1].trim());
                    int lowStockAlert = Integer.parseInt(values[2].trim());
                    String status = values.length > 3 ? values[3].trim() : "Available"; 

                    // Create a Medicine object and add it to the map
                    Medicine medicine = new Medicine(name, currentStock, lowStockAlert, status);
                    medicineMap.put(name, medicine); // Use medicine name as the key
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading medicine file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number in line: " + e.getMessage());
        }
    }

    // Method to view the inventory of medicines
    public void viewInventory() {
        System.out.println("\n=== Medicine Inventory ===");
        if (medicineMap.isEmpty()) {
            System.out.println("No medicines in inventory.");
        } else {
            for (Medicine medicine : medicineMap.values()) {
                System.out.println("Medicine Name: " + medicine.getMedicineName() +
                                   ", Current Stock: " + medicine.getCurrentStock() +
                                   ", Low Stock Alert: " + medicine.getLowStockAlert() +
                                   ", Status: " + medicine.getMedicineStatus());
            }
        }
    }
}