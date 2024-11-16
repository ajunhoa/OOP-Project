package model;

/**
 * The Pharmacist class represents a pharmacist in the hospital management system.
 * It extends the Staff class and includes specific attributes for pharmacists,
 * such as their ID and role.
 */
public class Pharmacist extends Staff {
    
    /**
     * Constructs a Pharmacist instance with the specified user ID, name, and role.
     *
     * @param userID The user ID of the pharmacist.
     * @param name The name of the pharmacist.
     * @param role The role of the pharmacist.
     */
    public Pharmacist(String userID, String name, String role) {
        super(userID, name, role, "", 0, 0, ""); 
    }

    /**
     * Gets the ID of the pharmacist.
     *
     * @return The pharmacist ID.
     */
    public String getPharmacistID() {
        return super.getId();
    }

    /**
     * Gets the role of the pharmacist.
     *
     * @return The role of the pharmacist.
     */
    public String getRole() {
        return super.getRole();
    }
}