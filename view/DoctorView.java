package view;

import controller.MedicalRecordController;
import model.Doctor;
import java.util.Scanner;
import model.AppointmentSlot;
import model.AppointmentOutcomeRecord;

public class DoctorView {
    private Doctor doctor;
    private Scanner scanner;
    private AppointmentSlot appointmentSlot = new AppointmentSlot();
    private AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord();
    private MedicalRecordController medicalRecordController;

    public DoctorView(Doctor doctor, MedicalRecordController medicalRecordController) {
        this.doctor = doctor;
        this.scanner = new Scanner(System.in);
        this.medicalRecordController = medicalRecordController;
    }

    public void displayDoctorMenu(String doctorID) {
        boolean exit = false;
        while (!exit) {
            this.displayMenu();
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Patient ID to view medical records: ");
                    String patientIdToView = scanner.nextLine().trim();
                    medicalRecordController.viewMedicalRecord(patientIdToView);
                    break;
                case 2:
                    System.out.print("Enter Patient ID to update medical records: ");
                    String patientIdToUpdate = scanner.nextLine().trim();

                    boolean backToMenu = false;
                    while (!backToMenu) {
                        System.out.println("\n=== Update Options ===");
                        System.out.println("1. Update Contact Info");
                        System.out.println("2. Update Contact Number");
                        System.out.println("3. Update Diagnoses");
                        System.out.println("4. Update Prescription");
                        System.out.println("5. Update Treatment");
                        System.out.println("6. Back to Main Menu");
                        System.out.print("Select an option: ");
                        int updateChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (updateChoice) {
                            case 1:
                                medicalRecordController.updateContactInfo(patientIdToUpdate);
                                break;
                            case 2:
                                medicalRecordController.updateContactNumber(patientIdToUpdate);
                                break;
                            case 3:
                                medicalRecordController.updateDiagnoses(patientIdToUpdate);
                                break;
                            case 4:
                                medicalRecordController.updatePrescription(patientIdToUpdate);
                                break;
                            case 5:
                                medicalRecordController.updateTreatment(patientIdToUpdate);
                                break;
                            case 6:
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
}
