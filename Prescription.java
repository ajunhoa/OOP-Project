class Prescription {
    private String medicationName;
    private String status = "pending";

    public Prescription(String medicationName) {
        this.medicationName = medicationName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Medication: " + medicationName + ", Status: " + status;
    }
}
