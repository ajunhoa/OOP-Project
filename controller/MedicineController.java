package controller;

import model.Medicine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    public void addMedicine(Scanner scanner) {
        viewInventory(); // Show current inventory before adding
        System.out.println("\n=== Add New Medicine ===");
        System.out.print("Enter Medicine Name: ");
        String medicineName = scanner.nextLine().trim(); // Trimmed input
    
        // Check if the medicine already exists
        if (medicineMap.containsKey(medicineName)) {
            System.out.println("Medicine with this name already exists.");
            return;
        }
    
        System.out.print("Enter Current Stock: ");
        int initialStock = scanner.nextInt();
        scanner.nextLine(); 
    
        System.out.print("Enter Low Stock Alert: ");
        int lowStockAlert = scanner.nextInt();
        scanner.nextLine(); 
    
        Medicine newMedicine = new Medicine(medicineName, initialStock, lowStockAlert, "Available");
    
        medicineMap.put(medicineName, newMedicine);
    
        saveMedicines();
    
        System.out.println("Medicine added successfully.");
    }

    public void deleteMedicine(Scanner scanner) {
        viewInventory(); // Show current inventory before deleting
        System.out.println("\n=== Delete Medicine ===");
        System.out.print("Enter Medicine Name: ");
        String medicineName = scanner.nextLine().trim(); // Trimmed input
    
        // Check if the medicine exists
        if (!medicineMap.containsKey(medicineName)) {
            System.out.println("Medicine with this name not found.");
            return;
        }

        medicineMap.remove(medicineName);

        saveMedicines();
    
        System.out.println("Medicine deleted successfully.");
    }

    public void updateStock(Scanner scanner) {
        viewInventory();
        System.out.println("\n=== Update Medicine Stock ===");
        System.out.print("Enter Medicine Name: ");
        String medicineName = scanner.nextLine().trim();
    
        // Check for case insensitive match
        Medicine medicine = medicineMap.values().stream()
            .filter(m -> m.getMedicineName().equalsIgnoreCase(medicineName))
            .findFirst()
            .orElse(null);
    
        if (medicine == null) {
            System.out.println("Medicine not found in inventory.");
            return;
        }
    
        System.out.println("Current Stock: " + medicine.getCurrentStock());
        System.out.print("Enter new stock level (or -1 to skip): ");
        int newStock = scanner.nextInt();
        scanner.nextLine(); 
        if (newStock != -1) {
            medicine.setCurrentStock(newStock);
        }
    
        System.out.print("Enter new low stock alert level (or -1 to skip): ");
        int newLowStockAlert = scanner.nextInt();
        scanner.nextLine(); 
        if (newLowStockAlert != -1) {
            medicine.setLowStockAlert(newLowStockAlert);
        }
    
        if (medicine.getCurrentStock() < medicine.getLowStockAlert()) {
            medicine.setMedicineStatus("Low"); // Update status to "Low"
        } else {
            medicine.setMedicineStatus("Available"); // Reset status if above alert level
        }
    
        saveMedicines();
    
        System.out.println("Stock updated successfully.");
    }

    private void saveMedicines() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(medicineFilePath))) {
            writer.write("Medicine Name,Current Stock,Low Stock Alert,Status\n");
            for (Medicine medicine : medicineMap.values()) {
                writer.write(medicine.getMedicineName() + "," +
                             medicine.getCurrentStock() + "," +
                             medicine.getLowStockAlert() + "," +
                             medicine.getMedicineStatus() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to medicine file: " + e.getMessage());
        }
    }


}