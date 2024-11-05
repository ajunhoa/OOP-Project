import java.util.Scanner;

public class PatientView {
   private Patient patient;

   public PatientView(Patient patient) {
      this.patient = patient;
   }

   public void handleUserChoice() {
      try (Scanner scanner = new Scanner(System.in)) {
         boolean isRunning = true;

         while (isRunning) {
            this.displayMenu();
            System.out.print("Enter your choice: ");

            // Error handling for invalid input
            try {
               int choice = scanner.nextInt();
               scanner.nextLine(); // Consume newline left-over
               switch (choice) {
                  case 1:
                     System.out.println("Medical Record:");
                     System.out.println(this.patient.viewMedicalRecord());
                     break;
                  case 2:
                     System.out.println("Update Contact Information");
                     System.out.print("Enter new contact number: ");
                     String contactNumber = scanner.nextLine();
                     System.out.print("Enter new email address: ");
                     String email = scanner.nextLine();
                     this.patient.updateContactInfo(contactNumber, email);
                     System.out.println("Contact information updated.");
                     break;
                  case 3:
                     System.out.println("Viewing available appointment slots...");
                     // Add logic to display available slots
                     break;
                  case 4:
                     System.out.println("Scheduling an appointment...");
                     // Add logic for scheduling an appointment
                     break;
                  case 5:
                     System.out.println("Rescheduling an appointment...");
                     // Add logic for rescheduling an appointment
                     break;
                  case 6:
                     System.out.println("Cancelling an appointment...");
                     // Add logic for cancelling an appointment
                     break;
                  case 7:
                     System.out.println("Viewing past appointment outcome records...");
                     // Add logic to view past appointment outcomes
                     break;
                  case 8:
                     isRunning = false;
                     System.out.println("Logging out...");
                     break;
                  default:
                     System.out.println("Invalid choice, please try again.");
               }
            } catch (Exception e) {
               System.out.println("Invalid input. Please enter a number from the menu.");
               scanner.nextLine(); // Clear invalid input
            }
         }
      }
   }

   public void displayMenu() {
      System.out.println("\n=== Patient Menu ===");
      System.out.println("1. View Medical Record");
      System.out.println("2. Update Personal Information");
      System.out.println("3. View Available Appointment Slots");
      System.out.println("4. Schedule an Appointment");
      System.out.println("5. Reschedule an Appointment");
      System.out.println("6. Cancel an Appointment");
      System.out.println("7. View Past Appointment Outcome Records");
      System.out.println("8. Logout");
      System.out.println("====================");
   }
}
