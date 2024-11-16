package model;

import java.util.Scanner;

/**
 * The Patient class represents a patient in the hospital management system.
 * It extends the User class and includes specific attributes and methods for patients,
 * such as managing their medical records.
 */
public class Patient extends User {

    /** The medical record associated with this patient. */
    private MedicalRecord medicalRecord;

    /**
     * Constructs a Patient instance with the specified attributes.
     *
     * @param id The ID of the patient.
     * @param name The name of the patient.
     * @param dateOfBirth The date of birth of the patient.
     * @param gender The gender of the patient.
     * @param bloodType The blood type of the patient.
     * @param contactInfo The contact information of the patient.
     * @param newUser Indicates if the patient is a new user (1 for yes, 0 for no).
     * @param password The password for the patient account.
     * @param contactNumber The contact number of the patient.
     */
    public Patient(String id, String name, String dateOfBirth, String gender, String bloodType, 
                   String contactInfo, int newUser, String password, int contactNumber) {
        super(id, name, "Patient", gender, 0, dateOfBirth, bloodType, contactInfo, password, newUser, contactNumber);
    }

    /**
     * Sets the medical record for this patient.
     *
     * @param medicalRecord The MedicalRecord object to associate with this patient.
     */
    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    /**
     * Gets the medical record associated with this patient.
     *
     * @return The medical record of the patient.
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * Allows the patient to update their contact email.
     *
     * @param scanner Scanner for user input.
     */
    public void updateContactEmail(Scanner scanner) {
        System.out.print("Enter new contact email: ");
        String newContactInfo = scanner.nextLine().trim();
        setContactInfo(newContactInfo);
        System.out.println("Contact email updated successfully.");
    }

    /**
     * Allows the patient to update their contact number.
     *
     * @param scanner Scanner for user input.
     */
    public void updateContactNumber(Scanner scanner) {
        System.out.print("Enter new contact number: ");
        String newContactNumber = scanner.nextLine().trim();
        try {
            int contactNumber = Integer.parseInt(newContactNumber);
            setContactNumber(contactNumber);
            System.out.println("Contact number updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid contact number. Please enter numeric values only.");
        }
    }
}
