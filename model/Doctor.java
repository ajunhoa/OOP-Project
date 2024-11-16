package model;

/**
 * The Doctor class represents a doctor in the hospital management system.
 * It extends the User class and includes specific attributes for doctors,
 * such as their ID and role.
 */
public class Doctor extends User {
    /** The ID of the doctor. */
    private String doctorID;
    
    /** The role of the doctor. */
    private String role;

    /**
     * Constructs a Doctor instance with the specified user ID, name, and role.
     *
     * @param userID The user ID of the doctor.
     * @param name The name of the doctor.
     * @param role The role of the doctor.
     */
    public Doctor(String userID, String name, String role) {
        super(userID, name);
        this.doctorID = userID;
        this.role = role;
    }

    /**
     * Gets the ID of the doctor.
     *
     * @return The doctor ID.
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Gets the role of the doctor.
     *
     * @return The role of the doctor.
     */
    public String getRole() {
        return role;
    }
}