package controller;

public class MedicineController {
    // Update the stock level for a specific medicine
    public static void updateStock(int newStock) {
        // Logic to update stock in the system (e.g., database or in-memory list)
        System.out.println("Stock level updated to " + newStock);
    }

    // Update the low stock alert level for a specific medicine
    public static void updateLowStockAlert(int newLowStockAlert) {
        // Logic to update low stock alert in the system
        System.out.println("Low stock alert level updated to " + newLowStockAlert);
    }

    // Update the status of a specific medicine based on stock level
    public static void updateMedicineStatus(String medicineStatus) {
        // Logic to update medicine status in the system
        System.out.println("Medicine status updated to " + medicineStatus);
    }
}

