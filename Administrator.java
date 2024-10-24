import java.util.List;

class Administrator extends User {
    private List<Doctor> doctors;
    private List<Pharmacist> pharmacists;

    public Administrator(String userID, String name) {
        super(userID, name);
    }

    public void manageStaff(User staff) {
        // Add, update, or remove staff
    }

    public void manageInventory(String medicine, int quantity) {
        // Code for managing inventory
    }

    public void approveReplenishmentRequest(String requestID) {
        // Code for approving request
    }

    @Override
    public void displayMenu() {
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointments Details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }
}
