    package model;

    public class Medicine {
        private String medicineName;
        private int currentStock;
        private int lowStockAlert;
        private String status;

        // Constructor
        public Medicine(String medicineName, int currentStock, int lowStockAlert, String status) {
            this.medicineName = medicineName;
            this.currentStock = currentStock;
            this.lowStockAlert = lowStockAlert;
            this.status = status;
        }

        // Getter for medicine name
        public String getMedicineName() {
            return medicineName;
        }

        // Getter for current stock
        public int getCurrentStock() {
            return currentStock;
        }

        // Setter for current stock
        public void setCurrentStock(int currentStock) {
            this.currentStock = currentStock;
        }

        // Getter for low stock alert
        public int getLowStockAlert() {
            return lowStockAlert;
        }

        // Setter for low stock alert
        public void setLowStockAlert(int lowStockAlert) {
            this.lowStockAlert = lowStockAlert;
        }

        // Getter for medicine status
        public String getMedicineStatus() {
            return status;
        }

        // Setter for medicine status
        public void setMedicineStatus(String status) {
            this.status = status;
        }
    }