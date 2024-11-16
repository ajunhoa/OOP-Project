package view;

import controller.MedicalRecordController;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import model.AppointmentOutcomeRecord;
import model.AppointmentSlot;
import model.Doctor;

/**
 * The DoctorView class provides the user interface for the doctor
 * in the hospital management system. It allows the doctor to manage 
 * patient records, view appointments, and update medical information.
 */
public class DoctorView {
    private Doctor doctor;
    private Scanner scanner;
    private AppointmentSlot appointmentSlot = new AppointmentSlot();
    private AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord();
    private MedicalRecordController medicalRecordController;

    private static final String appointmentFilePath = "assets/appointment.csv"; // Path to the CSV file

    /**
     * Constructs a DoctorView instance with the specified doctor and medical record controller.
     *
     * @param doctor The Doctor object representing the logged-in doctor.
     * @param medicalRecordController The MedicalRecordController object used to manage medical records.
     */
    public DoctorView(Doctor doctor, MedicalRecordController medicalRecordController) {
        this.doctor = doctor;
        this.scanner = new Scanner(System.in);
        this.medicalRecordController = medicalRecordController;
    }

    /**
     * Displays the doctor's menu and handles user input for various actions.
     *
     * @param doctorID The ID of the doctor for whom the menu is being displayed.
     */
    public void showMenu(String doctorID) {
        int pendingAppointments = appointmentSlot.countPendingAppointments(doctorID);
        if (pendingAppointments > 0) {
            System.out.println("You have " + pendingAppointments + " pending appointment(s).");
        }
        boolean exit = false;
        while (!exit) {
            this.displayMenu();
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewPatientsUnderDoctor(doctorID);
                    break;
                case 2:
                    System.out.print("Enter Patient ID to update medical records: ");
                    String patientIdToUpdate = scanner.nextLine().trim();

                    boolean backToMenu = false;
                    while (!backToMenu) {
                        System.out.println("\n=== Update Options ===");
                        System.out.println("1. Update Diagnoses");
                        System.out.println("2. Update Treatment");
                        System.out.println("3. Back to Main Menu");
                        System.out.print("Select an option: ");
                        int updateChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (updateChoice) {
                            case 1:
                                medicalRecordController.updateDiagnoses(patientIdToUpdate);
                                break;
                            case 2:
                                medicalRecordController.updateTreatment(patientIdToUpdate);
                                break;
                            case 3:
                                backToMenu = true;
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    }
                    break;
                case 3:
                    appointmentSlot.viewPersonalSchedule(doctorID);
                    break;
                case 4:
                    appointmentSlot.setAvailability(doctorID);
                    break;
                case 5:
                    appointmentSlot.manageAppointmentRequests(doctorID);
                    break;
                case 6:
                    appointmentSlot.viewUpcomingAppointment(doctorID);
                    break;
                case 7:
                    appointmentOutcomeRecord.recordAppointmentOutcome(doctorID);
                    break;
                case 8:
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the options available in the doctor's menu.
     */
    public void displayMenu() {
        System.out.println("\n=== Doctor Menu ===");
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Accept or Decline Appointment Requests");
        System.out.println("6. View Upcoming Appointments");
        System.out.println("7. Record Appointment Outcome");
        System.out.println("8. Logout");
    }

    /**
     * Displays the patients under the care of the specified doctor.
     *
     * @param doctorID The ID of the doctor whose patients are to be displayed.
     */
    private void viewPatientsUnderDoctor(String doctorID) {
        HashSet<String> patientIDs = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    String appointmentDoctorID = values[1].trim();
                    String patientID = values[2].trim();
                    String status = values[5].trim();

                    if (appointmentDoctorID.equalsIgnoreCase(doctorID) &&
                            (status.equalsIgnoreCase("Confirmed") || status.equalsIgnoreCase("Completed"))) {
                        patientIDs.add(patientID);
                    }
                }
            }

            // Display unique patient records
            if (patientIDs.isEmpty()) {
                System.out.println("No patients found under your care.");
            } else {
                System.out.println("\n=== Patients Under Your Care ===");
                for (String patientID : patientIDs) {
                    medicalRecordController.viewMedicalRecord(patientID);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    }
}