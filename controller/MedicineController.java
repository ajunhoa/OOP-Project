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

public class MedicineController {
    private final String medicineFilePath = "assets/medicine.csv";
    private Map<String, Medicine> medicineMap; 

    public MedicineController() {
        medicineMap = new HashMap<>();
        loadMedicines(); 
    }

    private void loadMedicines() {
        try (BufferedReader br = new BufferedReader(new FileReader(medicineFilePath))) {
            String line;
            br.readLine();

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

    private void saveReplenishmentRequest(String medicineName, int quantityRequested, String status) {
        String replenishRequestFilePath = "assets/replenish_request.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(replenishRequestFilePath, true))) {
            writer.write(medicineName + "," + quantityRequested + "," + status);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to replenish request file: " + e.getMessage());
        }
    }

    public void manageReplenishmentRequests(Scanner scanner) {
        List<String[]> pendingRequests = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader("assets/replenish_request.csv"))) {
            String line;
            br.readLine();
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



}

