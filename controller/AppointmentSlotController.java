package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.AppointmentSlot;

/**
 * The AppointmentSlotController class manages appointment scheduling, rescheduling, 
 * cancellation, and status updates for appointments in the hospital management system.
 */
public class AppointmentSlotController {
    
    private static final String appointmentFilePath = "assets/appointment.csv"; // Path to the appointment CSV file
    private Scanner scanner; // Scanner for user input
    private AppointmentSlot appointmentSlot; // AppointmentSlot model instance

    /**
     * Constructs an AppointmentSlotController instance and initializes the scanner and appointmentSlot.
     */
    public AppointmentSlotController() {
        this.scanner = new Scanner(System.in);
        this.appointmentSlot = new AppointmentSlot();
    }

    /**
     * Schedules a new appointment for the specified patient.
     *
     * @param patientID The ID of the patient for whom the appointment is being scheduled.
     */
    public void scheduleAppointment(String patientID) {
        List<String[]> availableSlots = new ArrayList<>();
        StaffController staffController = new StaffController(); 
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
    
            br.readLine(); // Skip header line
            System.out.println("Available Appointment Slots:");
    
            int displayCount = 1; 
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
    
                if (values.length >= 6 && (values[5].equalsIgnoreCase("Available") || values[5].equalsIgnoreCase("Cancelled"))) {
                    availableSlots.add(values); 
                    String doctorName = staffController.getDoctorNameByID(values[1]); 
                    System.out.println("Slot " + displayCount +
                                       ": Appointment ID: " + values[0] +
                                       ", Doctor Name: " + doctorName + 
                                       ", Date: " + values[3] +
                                       ", Time: " + values[4]);
                    displayCount++; 
                }
            }
    
            if (availableSlots.isEmpty()) {
                System.out.println("No available appointment slots found.");
                return;
            }
    
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
            return;
        }
    
        int choice = -1; // Initialize with an invalid choice
        boolean validInput = false;
    
        while (!validInput) {
            System.out.println("Select a slot number to schedule an appointment (or enter 0 to go back to the menu): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
    
                if (choice == 0) {
                    // Go back to the patient menu
                    System.out.println("Returning to the patient menu...");
                    return;
                } else if (choice >= 1 && choice <= availableSlots.size()) {
                    validInput = true; // Valid input, exit loop
                } else {
                    System.out.println("Invalid choice. Please select a number between 1 and " + availableSlots.size() + " or 0 to go back.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
    
        // Process the valid choice
        String[] selectedSlot = availableSlots.get(choice - 1); 
        String appointmentID = selectedSlot[0]; 
        String doctorID = selectedSlot[1]; 
    
        updateAppointmentStatus(appointmentID, doctorID, patientID.toUpperCase(), "Pending");
        System.out.println("Appointment " + appointmentID + " has been scheduled and status updated to 'Pending'.");
    }
    
    
    
    /**
     * Reschedules an existing appointment for the specified patient.
     *
     * @param patientID The ID of the patient whose appointment is being rescheduled.
     */
    public void rescheduleAppointment(String patientID) {
        List<String[]> currentAppointments = new ArrayList<>();
        StaffController staffController = new StaffController();
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
            br.readLine(); // Skip header line
            System.out.println("Current Appointments for Patient ID: " + patientID);
    
            int displayCount = 1;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[2].equalsIgnoreCase(patientID) &&
                    (values[5].equalsIgnoreCase("Pending") || values[5].equalsIgnoreCase("Confirmed"))) {
                    currentAppointments.add(values);
                    String doctorName = staffController.getDoctorNameByID(values[1]);
                    System.out.println(displayCount + ". Appointment ID: " + values[0] +
                                       ", Doctor Name: " + doctorName +
                                       ", Date: " + values[3] +
                                       ", Time: " + values[4] +
                                       ", Status: " + values[5]);
                    displayCount++;
                }
            }
    
            if (currentAppointments.isEmpty()) {
                System.out.println("No pending or confirmed appointments found for Patient ID: " + patientID);
                return;
            }
    
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
            return;
        }
    
        int choice = -1;
        boolean validInput = false;
    
        // Validate input for selecting the appointment to reschedule
        while (!validInput) {
            System.out.print("Select an appointment number to reschedule (or enter 0 to go back): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
    
                if (choice == 0) {
                    System.out.println("Returning to the main menu...");
                    return;
                } else if (choice >= 1 && choice <= currentAppointments.size()) {
                    validInput = true; // Valid input
                } else {
                    System.out.println("Invalid choice. Please select a number between 1 and " + currentAppointments.size() + " or 0 to go back.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
    
        String[] selectedAppointment = currentAppointments.get(choice - 1);
        String appointmentID = selectedAppointment[0];
        String doctorID = selectedAppointment[1];
    
        System.out.println("Available Appointment Slots:");
        List<String[]> availableSlots = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
            br.readLine(); // Skip header line
            int displayCount = 1;
    
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && (values[5].equalsIgnoreCase("Available") || values[5].equalsIgnoreCase("Cancelled"))) {
                    availableSlots.add(values);
                    String doctorName = staffController.getDoctorNameByID(values[1]);
                    System.out.println("Slot " + displayCount + ": Appointment ID: " + values[0] +
                                       ", Doctor Name: " + doctorName +
                                       ", Date: " + values[3] +
                                       ", Time: " + values[4]);
                    displayCount++;
                }
            }
    
            if (availableSlots.isEmpty()) {
                System.out.println("No available appointment slots to reschedule.");
                return;
            }
    
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
            return;
        }
    
        int newChoice = -1;
        validInput = false;
    
        // Validate input for selecting a new appointment slot
        while (!validInput) {
            System.out.print("Select a new slot number to reschedule to (or enter 0 to go back): ");
            if (scanner.hasNextInt()) {
                newChoice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
    
                if (newChoice == 0) {
                    System.out.println("Returning to the main menu...");
                    return;
                } else if (newChoice >= 1 && newChoice <= availableSlots.size()) {
                    validInput = true; // Valid input
                } else {
                    System.out.println("Invalid choice. Please select a number between 1 and " + availableSlots.size() + " or 0 to go back.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
    
        String[] newSlot = availableSlots.get(newChoice - 1);
        String newAppointmentID = newSlot[0];
        String newDoctorID = newSlot[1];
        String newDate = newSlot[3];
        String newTime = newSlot[4];
    
        updateAppointmentStatus(appointmentID, doctorID, null, "Available");
        updateAppointmentStatus(newAppointmentID, newDoctorID, patientID.toUpperCase(), "Pending");
    
        System.out.println("Appointment rescheduled successfully from Appointment ID: " + appointmentID +
                           " to Appointment ID: " + newAppointmentID + " on " + newDate + " at " + newTime + ".");
    }
    
    
    /**
     * Updates the status of an appointment.
     *
     * @param appointmentID The ID of the appointment to update.
     * @param doctorID The ID of the doctor associated with the appointment.
     * @param patientID The ID of the patient associated with the appointment, or null if not applicable.
     * @param status The new status of the appointment.
     */
    private void updateAppointmentStatus(String appointmentID, String doctorID, String patientID, String status) {
        List<String> lines = new ArrayList<>();
        boolean isUpdated = false;
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[0].equals(appointmentID)) {
                    values[5] = status;
    
                    if (patientID != null) {
                        values[2] = patientID;
                    } else {
                        values[2] = "";
                    }
    
                    line = String.join(",", values);
                    isUpdated = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointmentFilePath))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            if (isUpdated) {
                System.out.println("Appointment status updated successfully.");
            } else {
                System.out.println("No matching appointment found for ID: " + appointmentID);
            }
        } catch (IOException e) {
            System.out.println("Error writing to the appointment file: " + e.getMessage());
        }
    }

    /**
     * Canc els an existing appointment for the specified patient.
     *
     * @param patientID The ID of the patient whose appointment is being cancelled.
     */
    public void cancelAppointment(String patientID) {
        List<String[]> appointments = new ArrayList<>();
        StaffController staffController = new StaffController();
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
            br.readLine(); // Skip header line
            System.out.println("Appointments for Patient ID: " + patientID);
    
            int displayCount = 1;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[2].equalsIgnoreCase(patientID) &&
                    (values[5].equalsIgnoreCase("Pending") || values[5].equalsIgnoreCase("Confirmed"))) {
                    appointments.add(values);
                    String doctorName = staffController.getDoctorNameByID(values[1]);
                    System.out.println(displayCount + ". Appointment ID: " + values[0] +
                                       ", Doctor Name: " + doctorName +
                                       ", Date: " + values[3] +
                                       ", Time: " + values[4] +
                                       ", Status: " + values[5]);
                    displayCount++;
                }
            }
    
            if (appointments.isEmpty()) {
                System.out.println("No appointments found for Patient ID: " + patientID);
                return;
            }
    
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
            return;
        }
    
        int choice = -1;
        boolean validInput = false;
    
        // Input validation for selecting an appointment to cancel
        while (!validInput) {
            System.out.print("Select an appointment number to cancel (or enter 0 to go back): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
    
                if (choice == 0) {
                    System.out.println("Returning to the main menu...");
                    return;
                } else if (choice >= 1 && choice <= appointments.size()) {
                    validInput = true; // Valid input
                } else {
                    System.out.println("Invalid choice. Please select a number between 1 and " + appointments.size() + " or 0 to go back.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
    
        String[] selectedAppointment = appointments.get(choice - 1);
        String appointmentID = selectedAppointment[0];
        String doctorID = selectedAppointment[1];
    
        cancelAppointmentStatus(appointmentID, doctorID, null, "Cancelled");
    
        System.out.println("Appointment ID: " + appointmentID + " has been cancelled successfully.");
    }
    
    
    /**
     * Updates the status of a cancelled appointment.
     *
     * @param appointmentID The ID of the appointment to cancel.
     * @param doctorID The ID of the doctor associated with the appointment.
     * @param patientID The ID of the patient associated with the appointment, or null if not applicable.
     * @param status The new status of the appointment.
     */
    private void cancelAppointmentStatus(String appointmentID, String doctorID, String patientID, String status) {
        List<String> lines = new ArrayList<>();
        boolean isUpdated = false;
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[0].equals(appointmentID)) {
                    values[5] = status;

                    if (patientID != null) {
                        values[2] = patientID;
                    } else {
                        values[2] = "";
                    }

                    line = String.join(",", values); 
                    isUpdated = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
            return;
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointmentFilePath))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            if (isUpdated) {
                System.out.println("Appointment status updated successfully.");
            } else {
                System.out.println("No matching appointment found for ID: " + appointmentID);
            }
        } catch (IOException e) {
            System.out.println("Error writing to the appointment file: " + e.getMessage());
        }
    }
}