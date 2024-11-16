package model;

/**
 * The Medicine class represents a medicine in the hospital management system.
 * It contains information about the medicine's name, current stock, low stock alert level, and status.
 */
public class Medicine {
    /** The name of the medicine. */
    private String medicineName;

    /** The current stock level of the medicine. */
    private int currentStock;

    /** The low stock alert level for the medicine. */
    private int lowStockAlert;

    /** The status of the medicine (e.g., Available, Low, Pending). */
    private String status;

    /**
     * Constructs a Medicine instance with the specified name, current stock, low stock alert level, and status.
     *
     * @param medicineName The name of the medicine.
     * @param currentStock The current stock level of the medicine.
     * @param lowStockAlert The low stock alert level for the medicine.
     * @param status The status of the medicine.
     */
    public Medicine(String medicineName, int currentStock, int lowStockAlert, String status) {
        this.medicineName = medicineName;
        this.currentStock = currentStock;
        this.lowStockAlert = lowStockAlert;
        this.status = status;
    }

    /**
     * Gets the name of the medicine.
     *
     * @return The medicine name.
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * Gets the current stock level of the medicine.
     *
     * @return The current stock level.
     */
    public int getCurrentStock() {
        return currentStock;
    }

    /**
     * Sets the current stock level of the medicine.
     *
     * @param currentStock The new current stock level to set.
     */
    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    /**
     * Gets the low stock alert level for the medicine.
     *
     * @return The low stock alert level.
     */
    public int getLowStockAlert() {
        return lowStockAlert;
    }

    /**
     * Sets the low stock alert level for the medicine.
     *
     * @param lowStockAlert The new low stock alert level to set.
     */
    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

    /**
     * Gets the status of the medicine.
     *
     * @return The status of the medicine.
     */
    public String getMedicineStatus() {
        return status;
    }

    /**
     * Sets the status of the medicine.
     *
     * @param status The new status to set for the medicine.
     */
    public void setMedicineStatus(String status) {
        this.status = status;
    }
}