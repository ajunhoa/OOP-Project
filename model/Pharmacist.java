package model;

public class Pharmacist extends Staff {
    
    /**
     * Constructs a Pharmacist instance with the specified user ID, name, and role.
     *
     * @param userID The user ID of the pharmacist.
     * @param name The name of the pharmacist.
     * @param role The role of the pharmacist.
     */
    public Pharmacist(String userID, String name, String role) {
        super(userID, name, role, "", 0, 0, ""); // Call to the Staff constructor
    }

    /**
     * Gets the ID of the pharmacist.
     *
     * @return The pharmacist ID.
     */
    public String getPharmacistID() {
        return super.getId(); // Assuming getId() is defined in Staff
    }

    /**
     * Gets the role of the pharmacist.
     *
     * @return The role of the pharmacist.
     */
    public String getRole() {
        return super.getRole(); // Assuming getRole() is defined in Staff
    }
}