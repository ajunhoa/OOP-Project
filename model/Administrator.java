package model;

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
        super(userID, name, role, "", 0, 0, ""); // Call to the Staff constructor
        this.administratorID = userID; // Assuming userID is the same as administratorID
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
        return super.getRole(); // Calls the getRole() method from the Staff class
    }
}