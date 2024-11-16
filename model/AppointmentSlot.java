package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.StaffController;

/**
 * The AppointmentSlot class manages appointment scheduling within the hospital management system.
 * It provides functionalities to add appointments, view personal schedules, manage appointment requests,
 * and update appointment statuses.
 */
public class AppointmentSlot {
    
    /** The file path for storing appointment details. */
    private static final String appointment_filepath = "assets/appointment.csv";
    
    /** Scanner for reading user input. */
    private Scanner scanner;

    /**
     * Constructs an AppointmentSlot instance and initializes the scanner.
     */
    public AppointmentSlot() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Generates the next available appointment ID based on existing appointment IDs in the file.
     *
     * @return The next appointment ID in the format "APPT{number}".
     */
    private String getNextAppointmentID() {
        int maxId = 0; 
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
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
        return "APPT" + (maxId + 1); 
    }

    /**
     * Adds a new appointment for the specified doctor.
     *
     * @param doctorID The ID of the doctor for whom the appointment is being added.
     */
    public void addAppointment(String doctorID) {
        String appointmentID = getNextAppointmentID();
        String patientID = ""; 
        String date, time;
        String status = "Available"; 

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Date (DD/MM/YYYY): ");
        date = scanner.nextLine().trim();
        
        System.out.print("Enter Time (HH:MM AM/PM): ");
        time = scanner.nextLine().trim();

        updateCSV(appointmentID, doctorID, patientID, date, time, status);
    }

    /**
     * Updates the appointment CSV file with the new appointment details.
     *
     * @param appointmentID The ID of the appointment.
     * @param doctorID The ID of the doctor.
     * @param patientID The ID of the patient.
     * @param date The date of the appointment.
     * @param time The time of the appointment.
     * @param status The status of the appointment.
     */
    private void updateCSV(String appointmentID, String doctorID, String patientID, String date, String time, String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointment_filepath, true))) {
            writer.write(appointmentID + "," + doctorID + "," + patientID + "," + date + "," + time + "," + status);
            writer.newLine();
            System.out.println("Appointment added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the appointment file: " + e.getMessage());
        }
    }

    /**
     * Sets the availability for a specific doctor by adding an appointment.
     *
     * @param doctorID The ID of the doctor to set availability for.
     */
    public void setAvailability(String doctorID) {
        addAppointment(doctorID); 
    }

    /**
     * Views the personal schedule for a specific doctor.
     *
     * @param doctorID The ID of the doctor whose schedule is to be viewed.
     */
    public void viewPersonalSchedule(String doctorID) {
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
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

    /**
     * Views upcoming appointments for a specific doctor that are confirmed.
     *
     * @param doctorID The ID of the doctor whose upcoming appointments are to be viewed.
     */
    public void viewUpcomingAppointment(String doctorID) { 
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
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

    /**
     * Manages appointment requests for a specific doctor, allowing them to accept or decline appointments.
     *
     * @param doctorID The ID of the doctor managing appointment requests.
     */
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
            boolean validChoice = false; 
            while (!validChoice) { 
                System.out.println("Appointment ID: " + appointment[0] +
                        ", Patient ID: " + appointment[2] +
                        ", Date: " + appointment[3] +
                        ", Time: " + appointment[4]);
    
                System.out.print("Do you want to Accept (A) or Decline (D) this appointment? ");
                String choice = scanner.nextLine().trim().toUpperCase();
    
                if (choice.equals("A")) {
                    updateAppointmentStatus(appointment[0], "Confirmed");
                    System.out.println("Appointment " + appointment[0] + " has been confirmed.");
                    validChoice = true; 
                } else if (choice.equals("D")) {
                    updateAppointmentStatus(appointment[0], "Cancelled");
                    System.out.println("Appointment " + appointment[0] + " has been cancelled.");
                    validChoice = true; 
                } else {
                    System.out.println("Invalid choice. Please enter A or D.");
                }
            }
        }
    }

    /**
     * Updates the status of a specific appointment identified by its ID.
     *
     * @param appointmentID The ID of the appointment to update.
     * @param newStatus The new status to set for the appointment.
     */
    public void updateAppointmentStatus(String appointmentID, String newStatus) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[0].equals(appointmentID)) {
                    values[5] = newStatus; 
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

    /**
     * Views all appointments stored in the appointment file.
     */
    public void viewAllAppointments() {
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
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

    /**
     * Views available appointment slots for doctors.
     */
    public void viewAvailableAppointmentSlots() {
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
            System.out.println("Available Appointment Slots:");
    
            boolean hasAvailableSlots = false; 
            StaffController staffController = new StaffController(); 
    
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
    
                if (values.length >= 6) { 
                    String appointmentID = values[0];
                    String doctorID = values[1];
                    String patientID = values[2];
                    String date = values[3];
                    String time = values[4];
                    String status = values[5];
    
                    if (status.equalsIgnoreCase("Available") || status.equalsIgnoreCase("Cancelled")) {
                        String doctorName = staffController.getDoctorNameByID(doctorID); 
    
                        System.out.println("Appointment ID: " + appointmentID +
                                           ", Doctor Name: " + doctorName + 
                                           ", Date: " + date +
                                           ", Time: " + time);
                        hasAvailableSlots = true;
                    }
                }
            }
    
            if (!hasAvailableSlots) {
                System.out.println("No available appointment slots found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    }

    /**
     * Views scheduled appointments for a specific patient.
     *
     * @param patientID The ID of the patient whose scheduled appointments are to be viewed.
     */
    public void viewScheduledAppointments(String patientID) {
        List<String[]> scheduledAppointments = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
            System.out.println("Scheduled Appointments for Patient ID: " 
            + patientID);
            
            int displayCount = 1; 
            StaffController staffController = new StaffController(); 

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[2].equalsIgnoreCase(patientID) && 
                    (values[5].equalsIgnoreCase("Pending") || values[5].equalsIgnoreCase("Confirmed"))) {
                    scheduledAppointments.add(values);
                    String doctorName = staffController.getDoctorNameByID(values[1]); 
                    System.out.println(displayCount + 
                                       ": Appointment ID: " + values[0] +
                                       ", Doctor Name: " + doctorName + 
                                       ", Date: " + values[3] +
                                       ", Time: " + values[4] +
                                       ", Status: " + values[5]);
                    displayCount++;
                }
            }

            if (scheduledAppointments.isEmpty()) {
                System.out.println("No scheduled appointments found for Patient ID: " + patientID);
            }

        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
    }

    /**
     * Counts the number of pending appointments for a specific doctor.
     *
     * @param doctorID The ID of the doctor whose pending appointments are to be counted.
     * @return The number of pending appointments for the specified doctor.
     */
    public int countPendingAppointments(String doctorID) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(appointment_filepath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6 && values[1].equalsIgnoreCase(doctorID) && values[5].equalsIgnoreCase("Pending")) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the appointment file: " + e.getMessage());
        }
        return count;
    }
}