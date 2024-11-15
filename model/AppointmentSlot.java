package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppointmentSlot {
    
    private static final String appointment_filepath = "assets/appointment.csv";
    private Scanner scanner;
    public AppointmentSlot() {
        this.scanner = new Scanner(System.in);
    }

    // Method to get the next available Appointment ID
    private String getNextAppointmentID() {
        int maxId = 0; // Start with 0
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header if present
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0) {
                    try {
                        int currentId = Integer.parseInt(values[0].trim().replace("APPT", ""));
                        if (currentId > maxId) {
                            maxId = currentId;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing Appointment ID: " + values[0]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
        return "APPT" + (maxId + 1); // Increment and return the next ID
    }

    public void addAppointment(String doctorID) {
        String appointmentID = getNextAppointmentID();
        String patientID = ""; 
        String date, time;
        String status = "Available"; // Default status

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Date (DD/MM/YYYY): ");
        date = scanner.nextLine().trim();
        
        System.out.print("Enter Time (HH:MM AM/PM): ");
        time = scanner.nextLine().trim();

        updateCSV(appointmentID, doctorID, patientID, date, time, status);
    }

    private void updateCSV(String appointmentID, String doctorID, String patientID, String date, String time, String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointment_filepath, true))) {
            writer.write(appointmentID + "," + doctorID + "," + patientID + "," + date + "," + time + "," + status);
            writer.newLine();
            System.out.println("Appointment added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the appointment file: " + e.getMessage());
        }
    }

    public void setAvailability(String doctorID) {
        addAppointment(doctorID); 
    }

    public void viewPersonalSchedule(String doctorID) {

        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); 
            System.out.println("Upcoming Appointments for Doctor ID: " + doctorID);
    
            boolean hasAppointments = false; 
            int lineCount = 0; 
    
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] values = line.split(",");
    
                if (values.length >= 6) { // Ensure there are enough columns
                    String appointmentID = values[0];
                    String appointmentDoctorID = values[1];
                    String patientID = values[2];
                    String date = values[3];
                    String time = values[4];
                    String status = values[5];
    
                    if (appointmentDoctorID.equals(doctorID)) {
                        System.out.println("Appointment ID: " + appointmentID +
                                ", Patient ID: " + patientID +
                                ", Date: " + date +
                                ", Time: " + time +
                                ", Status: " + status);
                        hasAppointments = true;
                    }
                } else {
                    System.out.println("Skipping line " + lineCount + " due to insufficient columns.");
                }
            }
    
            if (!hasAppointments) {
                System.out.println("No appointments found for Doctor ID: " + doctorID);
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    }

    public void viewUpcomingAppointment(String doctorID) { 
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); 
            System.out.println("Upcoming Appointments for Doctor ID: " + doctorID);
    
            boolean hasAppointments = false; 
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] values = line.split(",");
    
                if (values.length >= 6) { 
                    String appointmentID = values[0];
                    String appointmentDoctorID = values[1];
                    String patientID = values[2];
                    String date = values[3];
                    String time = values[4];
                    String status = values[5];
 
                    if (appointmentDoctorID.equals(doctorID) && status.equalsIgnoreCase("Confirmed")) {
                        System.out.println("Appointment ID: " + appointmentID +
                                ", Patient ID: " + patientID +
                                ", Date: " + date +
                                ", Time: " + time +
                                ", Status: " + status);
                        hasAppointments = true;
                    }
                } else {
                    System.out.println("Skipping line " + lineCount + " due to insufficient columns.");
                }
            }
    
            if (!hasAppointments) {
                System.out.println("No available appointments found for Doctor ID: " + doctorID);
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    }

    public void manageAppointmentRequests(String doctorID) {
        List<String[]> pendingAppointments = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[1].equals(doctorID) && values[5].equalsIgnoreCase("Pending")) {
                    pendingAppointments.add(values);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    
        if (pendingAppointments.isEmpty()) {
            System.out.println("No pending appointment requests for Doctor ID: " + doctorID);
            return;
        }
    
        for (String[] appointment : pendingAppointments) {
            boolean validChoice = false; // Flag to track if a valid choice is made
            while (!validChoice) { // Loop until a valid choice is made
                System.out.println("Appointment ID: " + appointment[0] +
                        ", Patient ID: " + appointment[2] +
                        ", Date: " + appointment[3] +
                        ", Time: " + appointment[4]);
    
                System.out.print("Do you want to Accept (A) or Decline (D) this appointment? ");
                String choice = scanner.nextLine().trim().toUpperCase();
    
                if (choice.equals("A")) {
                    updateAppointmentStatus(appointment[0], "Confirmed");
                    System.out.println("Appointment " + appointment[0] + " has been confirmed.");
                    validChoice = true; // Set flag to true to exit the loop
                } else if (choice.equals("D")) {
                    updateAppointmentStatus(appointment[0], "Cancelled");
                    System.out.println("Appointment " + appointment[0] + " has been cancelled.");
                    validChoice = true; // Set flag to true to exit the loop
                } else {
                    System.out.println("Invalid choice. Please enter A or D.");
                    // The loop will continue prompting for a valid choice
                }
            }
        }
    }

    private void updateAppointmentStatus(String appointmentID, String newStatus) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[0].equals(appointmentID)) {
                    values[5] = newStatus; // Update the status
                    line = String.join(",", values);
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }

        // Write the updated lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointment_filepath))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            System.out.println("Appointment status updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the appointment file: " + e.getMessage());
        }
    }

    public void viewAllAppointments() {
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); 
            System.out.println("All Appointments:");
    
            boolean hasAppointments = false; 
            int lineCount = 0;
    
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] values = line.split(",");
    
                if (values.length >= 6) { 
                    String appointmentID = values[0];
                    String doctorID = values[1];
                    String patientID = values[2];
                    String date = values[3];
                    String time = values[4];
                    String status = values[5];
    
                    System.out.println("Appointment ID: " + appointmentID +
                                       ", Doctor ID: " + doctorID +
                                       ", Patient ID: " + patientID +
                                       ", Date: " + date +
                                       ", Time: " + time +
                                       ", Status: " + status);
                    hasAppointments = true;
                } else {
                    System.out.println("Skipping line " + lineCount + " due to insufficient columns.");
                }
            }
    
            if (!hasAppointments) {
                System.out.println("No appointments found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    }

    
    
}