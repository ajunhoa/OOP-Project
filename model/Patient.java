package model;

public class Patient extends User {

    public Patient(String id, String name, String dateOfBirth, String gender, String bloodType, 
                   String contactInfo, int newUser, String password, int contactNumber) {
        super(id, name, "Patient", gender, 0, dateOfBirth, bloodType, contactInfo, password, newUser, contactNumber);
    }

    @Override
    public String toString() {
        return "Patient ID: " + getId() + ", Name: " + getName() + ", Date of Birth: " + getDateOfBirth() +
               ", Gender: " + getGender() + ", Blood Type: " + getBloodType() +
               ", Contact Info: " + getContactInfo() + ", Contact Number: " + getContactNumber() +
               ", New User: " + (isNewUser() ? "Yes" : "No");
    }
}
