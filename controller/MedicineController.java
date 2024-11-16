
package controller;

import model.Medicine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 * The MedicineController class manages the inventory of medicines in the hospital management system.
 * It provides functionalities to load medicines from a file, view inventory, add, delete, update stock,
 * submit replenishment requests, and manage replenishment requests.
 */
public class MedicineController {
    /** The file path for the medicine inventory CSV file. */
    private final String medicineFilePath = "assets/medicine.csv";
    
    /** A map that stores medicines, keyed by medicine name. */
    private Map<String, Medicine> medicineMap; 

    /**
     * Constructs a MedicineController and initializes the medicine inventory.
     */
    public MedicineController() {
        medicineMap = new HashMap<>();
        loadMedicines(); 
    }

    /**
     * Loads medicines from the CSV file into the medicineMap.
     */
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

                    Medicine medicine = new Medicine(name, currentStock, lowStockAlert, status);
                    medicineMap.put(name, medicine); 
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading medicine file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number in line: " + e.getMessage());
        }
    }

    /**
     * Displays the current inventory of medicines.
     */
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

    /**
     * Adds a new medicine to the inventory.
     *
     * @param scanner A Scanner object for reading user input.
     */
    public void addMedicine(Scanner scanner) {
        viewInventory(); 
        System.out.println("\n=== Add New Medicine ===");
        System.out.print("Enter Medicine Name: ");
        String medicineName = scanner.nextLine().trim(); 
    
        if (medicineMap.containsKey(medicineName)) {
            System.out.println("Medicine with this name already exists.");
            return;
        }
    
        System.out.print("Enter Current Stock: ");
        int currentStock = scanner.nextInt();
        scanner.nextLine(); 
    
        System.out.print("Enter Low Stock Alert: ");
        int lowStockAlert = scanner.nextInt();
        scanner.nextLine(); 
    
        String status;
        if (currentStock < lowStockAlert) {
            status = "Low"; 
        } else {
            status = "Available";
        }
    
        Medicine newMedicine = new Medicine(medicineName, currentStock, lowStockAlert, status);
    
        medicineMap.put(medicineName, newMedicine);
    
        saveMedicines();
    
        System.out.println("Medicine added successfully.");
    }

    /**
     * Deletes a medicine from the inventory.
     *
     * @param scanner A Scanner object for reading user input.
     */
    public void deleteMedicine(Scanner scanner) {
        viewInventory();
        System.out.println("\n=== Delete Medicine ===");
        System.out.print("Enter Medicine Name: ");
        String medicineName = scanner.nextLine().trim();
    
        if (!medicineMap.containsKey(medicineName)) {
            System.out.println("Medicine with this name not found.");
            return;
        }

        medicineMap.remove(medicineName);

        saveMedicines();
    
        System.out.println("Medicine deleted successfully.");
    }

    /**
     * Updates the stock of a medicine in the inventory.
     *
     * @param scanner A Scanner object for reading user input.
     */
    public void updateStock(Scanner scanner) {
 viewInventory();
        System.out.println("\n=== Update Medicine Stock ===");
        System.out.print("Enter Medicine Name: ");
        String medicineName = scanner.nextLine().trim();
    
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
            medicine.setMedicineStatus("Low"); 
        } else {
            medicine.setMedicineStatus("Available");
        }
    
        saveMedicines();
    
        System.out.println("Stock updated successfully.");
    }

    /**
     * Saves the current medicine inventory to the CSV file.
     */
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

    /**
     * Submits a replenishment request for medicines that are low in stock.
     *
     * @param scanner A Scanner object for reading user input.
     */
    public void submitReplenishmentRequest(Scanner scanner) {
        List<Medicine> lowStockMedicines = new ArrayList<>();

        for (Medicine medicine : medicineMap.values()) {
            if (medicine.getMedicineStatus().equalsIgnoreCase("Low")) {
                lowStockMedicines.add(medicine);
            }
        }

        if (lowStockMedicines.isEmpty()) {
            System.out.println("No medicines require replenishment.");
            return;
        }

        System.out.println("\n=== Medicines Low in Stock ===");
        for (int i = 0; i < lowStockMedicines.size(); i++) {
            Medicine medicine = lowStockMedicines.get(i);
            System.out.println((i + 1) + ". " + medicine.getMedicineName() + 
                               " - Current Stock: " + medicine.getCurrentStock());
        }

        System.out.print("Select the number of the medicine to replenish: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        
        if (choice < 1 || choice > lowStockMedicines.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Medicine selectedMedicine = lowStockMedicines.get(choice - 1);

        System.out.print("Enter quantity to replenish: ");
        int quantityRequested = scanner.nextInt();
        scanner.nextLine(); 

        selectedMedicine.setMedicineStatus("Pending");

        saveReplenishmentRequest(selectedMedicine.getMedicineName(), quantityRequested, "Pending");

        saveMedicines();

        System.out.println("Replenishment request submitted successfully for " + selectedMedicine.getMedicineName());
    }

    /**
     * Saves a replenishment request to the replenish request file.
     *
     * @param medicineName The name of the medicine being requested for replenishment.
     * @param quantityRequested The quantity requested for replenishment.
     * @param status The status of the replenishment request.
     */
    private void saveReplenishmentRequest(String medicineName, int quantityRequested, String status) {
        String replenishRequestFilePath = "assets/replenish_request.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(replenishRequestFilePath, true))) {
            writer.write(medicineName + "," + quantityRequested + "," + status);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to replenish request file: " + e.getMessage());
        }
    }

    /**
     * Manages replenishment requests by allowing approval or rejection of pending requests.
     *
     * @param scanner A Scanner object for reading user input.
     */
 public void manageReplenishmentRequests(Scanner scanner) {
        List<String[]> pendingRequests = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader("assets/replenish_request.csv"))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3 && values[2].equalsIgnoreCase("Pending")) {
                    pendingRequests.add(values);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading replenish request file: " + e.getMessage());
            return;
        }
    
        if (pendingRequests.isEmpty()) {
            System.out.println("No pending replenishment requests found.");
            return;
        }
    
        System.out.println("\n=== Pending Replenishment Requests ===");
        for (int i = 0; i < pendingRequests.size(); i++) {
            String[] request = pendingRequests.get(i);
            System.out.println((i + 1) + ". Medicine: " + request[0] +
                               ", Quantity Requested: " + request[1] +
                               ", Status: " + request[2]);
        }
    
        System.out.print("Select the number of the request to approve/reject: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
    
        if (choice < 1 || choice > pendingRequests.size()) {
            System.out.println("Invalid choice.");
            return;
        }
    
        String[] selectedRequest = pendingRequests.get(choice - 1);
        System.out.print("Approve (A) or Reject (R) this request? ");
        String decision = scanner.nextLine().trim().toUpperCase();
    
        if (decision.equals("A")) {
            // Approve the request
            approveRequest(selectedRequest);
        } else if (decision.equals("R")) {
            // Reject the request
            rejectRequest(selectedRequest);
        } else {
            System.out.println("Invalid choice.");
        }
    }
    
    /**
     * Approves a replenishment request and updates the medicine stock accordingly.
     *
     * @param request The replenishment request to be approved.
     */
    private void approveRequest(String[] request) {
        String medicineName = request[0];
        int quantityRequested = Integer.parseInt(request[1]);
    
        // Update the medicine stock
        Medicine medicine = medicineMap.get(medicineName);
        if (medicine != null) {
            int newStock = medicine.getCurrentStock() + quantityRequested;
            medicine.setCurrentStock(newStock);
            medicine.setMedicineStatus(newStock < medicine.getLowStockAlert() ? "Low" : "Available");
        }
    
        updateReplenishmentRequestStatus(request[0], "Approved");
    
        saveMedicines();
    
        System.out.println("Replenishment request for " + medicineName + " has been approved.");
    }
    
    /**
     * Rejects a replenishment request and updates the status accordingly.
     *
     * @param request The replenishment request to be rejected.
     */
    private void rejectRequest(String[] request) {
        String medicineName = request[0];
    
        updateReplenishmentRequestStatus(medicineName, "Rejected");
    
        Medicine medicine = medicineMap.get(medicineName);
        if (medicine != null) {
            medicine.setMedicineStatus(medicine.getCurrentStock() < medicine.getLowStockAlert() ? "Low" : "Available");
        }
    
        saveMedicines();
    
        System.out.println("Replenishment request for " + medicineName + " has been rejected.");
    }
    
    /**
     * Updates the status of a replenishment request in the request file.
     *
     * @param medicineName The name of the medicine associated with the request.
     * @param newStatus The new status to set for the request.
     */
    private void updateReplenishmentRequestStatus(String medicineName, String newStatus) {
        List<String> lines = new ArrayList<>();
        boolean isUpdated = false;
        try (BufferedReader br = new BufferedReader(new FileReader("assets/replenish_request.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3 && values[0].equalsIgnoreCase(medicineName)) {
                    values[2] = newStatus; 
                    line = String.join(",", values);
                    isUpdated = true; 
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading replenish request file: " + e.getMessage());
            return;
        }
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("assets/replenish_request.csv"))) {
            for (String updatedLine : lines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to replenish request file: " + e.getMessage());
        }
    
        if (isUpdated) {
            System.out.println("Replenishment request status updated to " + newStatus + " for " + medicineName);
        } else {
            System.out.println("No matching replenishment request found for " + medicineName);
        }
    }

    /**
     * Counts the number of medicines that are low in stock.
     *
     * @return The count of low stock medicines.
     */
    public int countLowStockMedicines() {
        int count = 0;
        for (Medicine medicine : medicineMap.values()) {
            if (medicine.getMedicineStatus().equalsIgnoreCase("Low")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of pending replenishment requests.
     *
     * @return The count of pending replenishment requests.
     */
    public int countPendingReplenishmentRequests() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("assets/replenish_request.csv"))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3 && values[2].equalsIgnoreCase("Pending")) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading replenish request file: " + e.getMessage());
        }
        return count;
    }
}