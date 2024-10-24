import java.util.List;

class Pharmacist extends User {
    private List<Medication> inventory;

    public Pharmacist(String userID, String name) {
        super(userID, name);
    }

    public void viewAppointmentOutcomeRecord(Appointment appointment) {
        System.out.println(appointment.getOutcome());
    }

    public void updatePrescriptionStatus(Prescription prescription, String status) {
        prescription.setStatus(status);
    }

    public void submitReplenishmentRequest(String medicineName, int quantity) {
        // Code for submitting replenishment request
    }

    @Override
    public void displayMenu() {
        System.out.println("1. View Appointment Outcome Record");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Logout");
    }
}
