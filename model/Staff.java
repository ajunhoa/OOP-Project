package model;

/**
 * The Staff class represents a staff member in the hospital management system.
 * It extends the User class and includes specific attributes and methods for staff members.
 */
public class Staff extends User {
    public String id;
    public String name;
    /**
     * Constructs a Staff instance with the specified attributes.
     *
     * @param id The ID of the staff member.
     * @param name The name of the staff member.
     * @param role The role of the staff member (e.g., Doctor, Pharmacist).
     * @param gender The gender of the staff member.
     * @param age The age of the staff member.
     * @param newUser  Indicates if the staff member is a new user (1 for yes, 0 for no).
     * @param password The password for the staff member's account.
     * 
     */
    public Staff(String id, String name, String role, String gender, int age, int newUser , String password) {
        super(id, name, role, gender, age, "", "", "", password, newUser , 0);
    }

        /**
     * Constructs a Staff instance with the specified ID and name.
     *
     * @param id The ID of the staff member.
     * @param name The name of the staff member.
     */
    public Staff(String id, String name) {
        super(id, name, "", "", 0, "", "", "", "", 0, 0);
        this.id = id;
        this.name = name;
    }

    /**
     * Returns a string representation of the staff member, including their details.
     *
     * @return A string containing the staff member's ID, name, role, gender, age, and whether they are a new user.
     */
    @Override
    public String toString() {
        return "Staff ID: " + getId() + ", Name: " + getName() + ", Role: " + getRole() + 
               ", Gender: " + getGender() + ", Age: " + getAge() + ", New User: " + (isNewUser () ? "Yes" : "No");
    }
}