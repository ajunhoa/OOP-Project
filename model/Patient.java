package model;
        
    public class Patient extends User {

        private MedicalRecord medicalRecord; // To hold the linked medical record
                public Patient(String id, String name, String dateOfBirth, String gender, String bloodType, 
                    String contactInfo, int newUser, String password, int contactNumber) {
                super(id, name, "Patient", gender, 0, dateOfBirth, bloodType, contactInfo, password, newUser, contactNumber);
            }
        
            // Method to view medical record
        public void viewMedicalRecord() {
            System.out.println("Fetching Medical Record for Patient: " + getId());
            if (medicalRecord != null) {
                medicalRecord.displayMedicalRecord(); // Show the medical record
            } else {
                System.out.println("No medical record found for patient.");
            }
        }
            
        
            // Setter method to link the medical record to this patient
        public void setMedicalRecord(MedicalRecord medicalRecord) {
            this.medicalRecord = medicalRecord; 
            if (medicalRecord != null) {
                System.out.println("Medical record set for patient: " + getName());
            } else {
                System.out.println("No medical record found for patient: " + getName());
            }
        }
        
            // Getter method for medical record
        public MedicalRecord getMedicalRecord() {
            return medicalRecord;
        }
        
        @Override
        public String toString() {
            return "Patient ID: " + getId() + ", Name: " + getName() + ", Date of Birth: " + getDateOfBirth() +
                    ", Gender: " + getGender() + ", Blood Type: " + getBloodType() +
                    ", Contact Info: " + getContactInfo() + ", Contact Number: " + getContactNumber() +
                    ", New User: " + (isNewUser() ? "Yes" : "No");
        }
    }
        