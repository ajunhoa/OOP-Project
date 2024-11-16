package model;

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
     * @param newUser  Indicates if the patient is a new user (1 for yes, 0 for no).
     * @param password The password for the patient account.
     * @param contactNumber The contact number of the patient.
     */
    public Patient(String id, String name, String dateOfBirth, String gender, String bloodType, 
                   String contactInfo, int newUser , String password, int contactNumber) {
        super(id, name, "Patient", gender, 0, dateOfBirth, bloodType, contactInfo, password, newUser , contactNumber);
    }

    /**
     * Displays the medical record for this patient.
     * If no medical record is found, a message is displayed indicating this.
     */
    public void viewMedicalRecord() {
        System.out.println("Fetching Medical Record for Patient: " + getId());
        if (medicalRecord != null) {
            medicalRecord.displayMedicalRecord(this); 
        } else {
            System.out.println("No medical record found for patient.");
        }
    }
    

    /**
     * Sets the medical record for this patient.
     *
     * @param medicalRecord The MedicalRecord object to associate with this patient.
     */
    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord; 
        if (medicalRecord != null) {
            // System.out.println("Medical record set for patient: " + getName());
        } else {
            System.out.println("No medical record found for patient: " + getName());
        }
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
     * Returns a string representation of the patient, including their details.
     *
     * @return A string containing the patient's ID, name, date of birth, gender, blood type,
     *         contact information, contact number, and whether they are a new user.
     */
    @Override
    public String toString() {
        return "Patient ID: " + getId() + ", Name: " + getName() + ", Date of Birth: " + getDateOfBirth() +
                ", Gender: " + getGender() + ", Blood Type: " + getBloodType() +
                ", Contact Info: " + getContactInfo() + ", Contact Number: " + getContactNumber() +
                ", New User: " + (isNewUser () ? "Yes" : "No");
    }
}