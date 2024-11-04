import java.util.Scanner;

public class PatientView {
    private Patient patient;

    public PatientView(Patient patient) {
        this.patient = patient;
    }

    public void handleUserChoice() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            displayMenu(); // Call to display the menu
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Medical Record:");
                    System.out.println(patient.viewMedicalRecord());
                    break;
                case 2:
                    System.out.println("Update Contact Information");
                    System.out.print("Enter new contact number: ");
                    String contactNumber = scanner.next();
                    System.out.print("Enter new email address: ");
                    String email = scanner.next();
                    patient.updateContactInfo(contactNumber, email);
                    System.out.println("Contact information updated.");
                    break;
                case 3:
                    System.out.println("Viewing available appointment slots...");
                    break;
                case 4:
                    System.out.println("Scheduling an appointment...");
                    break;
                case 5:
                    System.out.println("Rescheduling an appointment...");
                    break;
                case 6:
                    System.out.println("Cancelling an appointment...");
                    break;
                case 7:
                    System.out.println("Viewing past appointment records...");
                    break;
                case 8:
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }

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
}
