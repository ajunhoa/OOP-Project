
package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import model.MedicalRecord;
import model.Patient;
import model.User;
/**
 * The MedicalRecordController class manages the medical records and user information
 * for patients in the hospital management system. It provides functionalities to update
 * patient contact information, diagnoses, prescriptions, and treatments, as well as
 * saving the updated records to files.
 */
public class MedicalRecordController {
    
    /** A map that stores medical records, keyed by patient ID. */
    private Map<String, MedicalRecord> medicalRecords;
    
    /** A map that stores user information, keyed by user ID. */
    private Map<String, User> users; 
    
    /** A Scanner object for reading user input. */
    private Scanner scanner;
    
    /** The file path for saving medical records. */
    private String medicalRecordFilePath; 
    
    /** The file path for saving user records. */
    private String userFilePath; 

    /**
     * Constructs a MedicalRecordController with the specified medical records, users,
     * and file paths for medical records and user records.
     *
     * @param medicalRecords A map of medical records keyed by patient ID.
     * @param users A map of user information keyed by user ID.
     * @param medicalRecordFilePath The file path for saving medical records.
     * @param userFilePath The file path for saving user records.
     */
    public MedicalRecordController(Map<String, MedicalRecord> medicalRecords, Map<String, User> users, String medicalRecordFilePath, String userFilePath) {
        this.medicalRecords = medicalRecords;
        this.users = users;
        this.scanner = new Scanner(System.in);
        this.medicalRecordFilePath = medicalRecordFilePath;
        this.userFilePath = userFilePath;
    }

    /**
     * Updates the contact email of the patient identified by the specified patient ID.
     *
     * @param patientId The ID of the patient whose contact email is to be updated.
     */
    public void updateContactInfo(String patientId) {
        User user = users.get(patientId);
        if (user instanceof Patient) {
            System.out.print("Enter new contact Email: ");
            String newContactInfo = scanner.nextLine().trim();
            user.setContactInfo(newContactInfo); 
            System.out.println("Email updated successfully.");
            saveUserRecordsToFile(); 
        } else {
            System.out.println("No user record found for Patient ID: " + patientId);
        }
    }

    /**
     * Updates the contact number of the patient identified by the specified patient ID.
     *
     * @param patientId The ID of the patient whose contact number is to be updated.
     */
    public void updateContactNumber(String patientId) {
        User user = users.get(patientId);
        if (user instanceof Patient) {
            System.out.print("Enter new contact number: ");
            String newContactNumber = scanner.nextLine().trim();
            try {
                int contactNumber = Integer.parseInt(newContactNumber);
                user.setContactNumber(contactNumber);
                System.out.println("Contact number updated successfully.");
                saveUserRecordsToFile(); 
            } catch (NumberFormatException e) {
                System.out.println("Invalid contact number. Please enter numeric values only.");
            }
        } else {
            System.out.println("No user record found for Patient ID: " + patientId);
        }
    }

    /**
     * Updates the diagnoses of the medical record for the patient identified by the specified patient ID.
     *
     * @param patientId The ID of the patient whose diagnoses are to be updated.
     */
    public void updateDiagnoses(String patientId) {
        MedicalRecord record = medicalRecords.get(patientId);
        if (record != null) {
            System.out.print("Enter new diagnoses: ");
            String newDiagnoses = scanner.nextLine().trim();
            record.setPastDiagnoses(newDiagnoses); 
            System.out.println("Diagnoses updated successfully.");
            saveMedicalRecordsToFile();
        } else {
            System.out.println("No medical record found for Patient ID: " + patientId);
        }
    }

    /**
     * Updates the prescription of the medical record for the patient identified by the specified patient ID.
     *
     * @param patientId The ID of the patient whose prescription is to be updated.
     */
    public void updatePrescription(String patientId) {
        MedicalRecord record = medicalRecords.get(patientId);
        if (record != null) {
            System.out.print("Enter new prescription: ");
            String newPrescription = scanner.nextLine().trim();
            System.out.println("Prescription updated successfully.");
            saveMedicalRecordsToFile(); 
        } else {
            System.out.println("No medical record found for Patient ID: " + patientId);
        }
    }

    /**
     * Updates the treatment of the medical record for the patient identified by the specified patient ID.
     *
     * @param patientId The ID of the patient whose treatment is to be updated.
     */
    public void updateTreatment(String patientId) {
        MedicalRecord record = medicalRecords.get(patientId);
        if (record != null) {
            System.out.print("Enter new treatment: ");
            String newTreatment = scanner.nextLine().trim();
            record.setTreatment(newTreatment); 
            System.out.println("Treatment updated successfully.");
            saveMedicalRecordsToFile();
        } else {
            System.out.println("No medical record found for Patient ID: " + patientId);
        }
    }

    /**
     * Saves the current medical records to the specified file.
     */
    public void saveMedicalRecordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(medicalRecordFilePath))) {
            writer.write("Patient ID,Past Diagnoses,Treatment");
            writer.newLine();

            for (MedicalRecord record : medicalRecords.values()) {
                String line = record.getPatientId() + "," +
                              record.getPastDiagnoses() + "," +
                              record.getTreatment();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Medical records have been saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the medical records file: " + e.getMessage());
        }
    }
    
    /**
     * Saves the current user records to the specified file.
     */
    public void saveUserRecordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFilePath))) {
            writer.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Contact Info,New User,Password,Contact Number");
            writer.newLine();

            for (User  user : users.values()) {
                if (user instanceof Patient) { 
                    String line = user.getId() + "," +
                                  user.getName() + "," +
                                  user.getDateOfBirth() + "," +
                                  user.getGender() + "," +
                                  user.getBloodType() + "," +
                                  user.getContactInfo() + "," +
                                  (user.isNewUser () ? "1" : "0") + "," +
                                  user.getPassword() + "," +
                                  user.getContactNumber();
                    writer.write(line);
                    writer.newLine();
                }
            }
            System.out.println("Patient records have been saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the patient list file: " + e.getMessage());
        }
    }

    /**
     * Displays the medical record for the patient identified by the specified patient ID.
     *
     * @param patientId The ID of the patient whose medical record is to be viewed.
     */
    public void viewMedicalRecord(String patientId) {
        MedicalRecord record = medicalRecords.get(patientId);
        if (record != null) {
            System.out.println("\n=== Medical Record ===");
            System.out.println("Patient ID: " + record.getPatientId());
            System.out.println("Past Diagnoses: " + record.getPastDiagnoses());
            System.out.println("Treatment: " + record.getTreatment());
    
            User user = users.get(patientId);
            if (user instanceof Patient) {
                Patient patient = (Patient) user;
                System.out.println("Name: " + patient.getName());
                System.out.println("Date of Birth: " + patient.getDateOfBirth());
                System.out.println("Gender: " + patient.getGender());
                System.out.println("Contact Information: " + patient.getContactInfo());
                System.out.println("Blood Type: " + patient.getBloodType());
            }
        } else {
            System.out.println("No medical record found for Patient ID: " + patientId);
        }
    }
    public boolean isPatientIdValid(String patientId) {
        // Check both users and medicalRecords to ensure a patient ID exists
        return users.containsKey(patientId) && users.get(patientId) instanceof Patient
                && medicalRecords.containsKey(patientId);
    }
    
}