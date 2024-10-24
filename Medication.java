class Medication {
    private String name;
    private int quantity;
    private int lowStockThreshold;

    public Medication(String name, int quantity, int lowStockThreshold) {
        this.name = name;
        this.quantity = quantity;
        this.lowStockThreshold = lowStockThreshold;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isLowStock() {
        return quantity <= lowStockThreshold;
    }

    @Override
    public String toString() {
        return "Medication: " + name + ", Quantity: " + quantity;
    }
}
