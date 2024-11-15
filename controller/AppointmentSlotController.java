package controller;

import model.AppointmentSlot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppointmentSlotController {
    
    private static final String appointmentFilePath = "assets/appointment.csv";
    private Scanner scanner;
    private AppointmentSlot appointmentSlot;

    public AppointmentSlotController() {
        this.scanner = new Scanner(System.in);
        this.appointmentSlot = new AppointmentSlot();
    }

    public void scheduleAppointment(String patientID) {
        List<String[]> availableSlots = new ArrayList<>();
        StaffController staffController = new StaffController(); // Create an instance of StaffController
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
            br.readLine(); 
            System.out.println("Available Appointment Slots:");
    
            int displayCount = 1; 
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
    
                if (values.length >= 6 && values[5].equalsIgnoreCase("Available")) {
                    availableSlots.add(values); 
                    String doctorName = staffController.getDoctorNameByID(values[1]); // Get doctor name using doctor ID
                    System.out.println("Slot " + displayCount +
                                       ": Appointment ID: " + values[0] +
                                       ", Doctor Name: " + doctorName + // Display doctor name
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
    
        System.out.print("Select a slot number to schedule an appointment: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
    
        if (choice < 1 || choice > availableSlots.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
    
        String[] selectedSlot = availableSlots.get(choice - 1); 
        String appointmentID = selectedSlot[0]; 
        String doctorID = selectedSlot[1]; 
    
        updateAppointmentStatus(appointmentID, doctorID, patientID.toUpperCase(), "Pending");
        System.out.println("Appointment " + appointmentID + " has been scheduled and status updated to 'Pending'.");
    }
    
    public void rescheduleAppointment(String patientID) {
        List<String[]> currentAppointments = new ArrayList<>();
        StaffController staffController = new StaffController(); // Create an instance of StaffController
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
            br.readLine();
            System.out.println("Current Appointments for Patient ID: " + patientID);
    
            int displayCount = 1;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // Update the condition to check for both Pending and Confirmed statuses
                if (values.length >= 6 && values[2].equalsIgnoreCase(patientID) && 
                    (values[5].equalsIgnoreCase("Pending") || values[5].equalsIgnoreCase("Confirmed"))) {
                    currentAppointments.add(values);
                    String doctorName = staffController.getDoctorNameByID(values[1]); // Get doctor name using doctor ID
                    System.out.println(displayCount + ". Appointment ID: " + values[0] +
                                       ", Doctor Name: " + doctorName + // Display doctor name
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
    
        System.out.print("Select an appointment number to reschedule: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
    
        if (choice < 1 || choice > currentAppointments.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
    
        String[] selectedAppointment = currentAppointments.get(choice - 1);
        String appointmentID = selectedAppointment[0];
        String doctorID = selectedAppointment[1]; 
    
        System.out.println("Available Appointment Slots:");
        List<String[]> availableSlots = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
            String line;
            br.readLine();
            int displayCount = 1;
    
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[5].equalsIgnoreCase("Available")) {
                    availableSlots.add(values);
                    String doctorName = staffController.getDoctorNameByID(values[1]); // Get doctor name using doctor ID
                    System.out.println("Slot " + displayCount + ": Appointment ID: " + values[0] +
                                       ", Doctor Name: " + doctorName + // Display doctor name
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
    
        System.out.print("Select a new slot number to reschedule to: ");
        int newChoice = scanner.nextInt();
        scanner.nextLine();
    
        if (newChoice < 1 || newChoice > availableSlots.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
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

    public void cancelAppointment(String patientID) {
    List<String[]> appointments = new ArrayList<>();
    StaffController staffController = new StaffController(); // Create an instance of StaffController

    try (BufferedReader br = new BufferedReader(new FileReader(appointmentFilePath))) {
        String line;
        br.readLine();
        System.out.println("Appointments for Patient ID: " + patientID);

        int displayCount = 1;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length >= 6 && values[2].equalsIgnoreCase(patientID) && 
                (values[5].equalsIgnoreCase("Pending") || values[5].equalsIgnoreCase("Confirmed"))) {
                appointments.add(values);
                String doctorName = staffController.getDoctorNameByID(values[1]); // Get doctor name using doctor ID
                System.out.println(displayCount + ". Appointment ID: " + values[0] +
                                   ", Doctor Name: " + doctorName + // Display doctor name
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

    System.out.print("Select an appointment number to cancel: ");
    int choice = scanner.nextInt();
    scanner.nextLine();

    if (choice < 1 || choice > appointments.size()) {
        System.out.println("Invalid choice. Please try again.");
        return;
    }

    String[] selectedAppointment = appointments.get(choice - 1);
    String appointmentID = selectedAppointment[0];
    String doctorID = selectedAppointment[1];

    cancelAppointmentStatus(appointmentID, doctorID, null, "Cancelled");

    System.out.println("Appointment ID: " + appointmentID + " has been cancelled successfully.");
}
    
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