import java.util.Scanner;

public class PatientView {
    private Patient patient;
    private Scanner scanner;

    public PatientView(Patient patient) {
        this.patient = patient;
        this.scanner = new Scanner(System.in);
    }

    // Method to display the menu
    public void displayMenu() {
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Past Appointment Outcome Records");
        System.out.println("8. Logout");
    }

    // Method to handle the user’s choice
    public void handleUserChoice() {
        boolean exit = false;
        while (!exit) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    updateContactInfo();
                    break;
                case 3:
                    viewAppointmentSlots();
                    break;
                case 4:
                    scheduleAppointment();
                    break;
                case 5:
                    rescheduleAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    viewPastAppointmentRecords();
                    break;
                case 8:
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Method to view medical record
    private void viewMedicalRecord() {
        System.out.println("Medical Record:");
        System.out.println(patient.viewMedicalRecord());
    }

    // Method to update contact information
    private void updateContactInfo() {
        System.out.println("Update Contact Information");
        System.out.print("Enter new contact number: ");
        String contactNumber = scanner.next();
        System.out.print("Enter new email address: ");
        String email = scanner.next();
     
