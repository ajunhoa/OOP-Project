    package model;

    public class Medicine {
        private String medicineName;
        private int currentStock;
        private int lowStockAlert;
        private String status;

        public Medicine(String medicineName, int currentStock, int lowStockAlert, String status) {
            this.medicineName = medicineName;
            this.currentStock = currentStock;
            this.lowStockAlert = lowStockAlert;
            this.status = status;
        }

        public String getMedicineName() {
            return medicineName;
        }

        public int getCurrentStock() {
            return currentStock;
        }

        public void setCurrentStock(int currentStock) {
            this.currentStock = currentStock;
        }

        public int getLowStockAlert() {
            return lowStockAlert;
        }

        public void setLowStockAlert(int lowStockAlert) {
            this.lowStockAlert = lowStockAlert;
        }

        public String getMedicineStatus() {
            return status;
        }

        public void setMedicineStatus(String status) {
            this.status = status;
        }
    }