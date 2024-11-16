package model;

/**
 * The Administrator class represents an administrator in the hospital management system.
 * It extends the Staff class and includes specific attributes for administrators,
 * such as their ID and role.
 */
public class Administrator extends Staff {
    
    /** The ID of the administrator. */
    private String administratorID;

    /**
     * Constructs an Administrator instance with the specified user ID, name, and role.
     *
     * @param userID The user ID of the administrator.
     * @param name The name of the administrator.
     * @param role The role of the administrator.
     */
    public Administrator(String userID, String name, String role) {
        super(userID, name, role, "", 0, 0, ""); 
        this.administratorID = userID; 
    }

    /**
     * Gets the ID of the administrator.
     *
     * @return The administrator ID.
     */
    public String getAdministratorID() {
        return administratorID;
    }

    /**
     * Gets the role of the administrator.
     *
     * @return The role of the administrator.
     */
    @Override
    public String getRole() {
        return super.getRole(); 
    }
}