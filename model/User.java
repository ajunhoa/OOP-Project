package model;

/**
 * The User class represents a user in the hospital management system.
 * It contains information about the user's identity, contact details, 
 * role, and other personal attributes.
 * <p>
 * This class provides methods for creating a user, accessing and modifying 
 * user attributes, validating passwords, and determining if a user is new.
 * </p>
 */
public class User {
    private String id;
    private String name;
    private String role;
    private String gender;
    private int age;
    private String dateOfBirth;
    private String bloodType;
    private String contactInfo;
    private String password;
    private int contactNumber;
    private int newUser ;

    /**
     * Constructs a User instance with the specified attributes.
     *
     * @param id           The unique identifier for the user.
     * @param name         The name of the user.
     * @param role         The role of the user (e.g., Patient, Staff).
     * @param gender       The gender of the user.
     * @param age          The age of the user.
     * @param dateOfBirth  The date of birth of the user.
     * @param bloodType    The blood type of the user.
     * @param contactInfo  The contact information of the user.
     * @param password     The password for the user account.
     * @param newUser       Indicates if the user is a new user (1 for yes, 0 for no).
     * @param contactNumber The contact number of the user.
     */
    public User(String id, String name, String role, String gender, int age, String dateOfBirth, 
                String bloodType, String contactInfo, String password, int newUser , int contactNumber) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.password = password;
        this.newUser  = newUser ;
        this.contactNumber = contactNumber;
    }

    /**
     * Constructs a User instance with the specified ID and name.
     *
     * @param id   The unique identifier for the user.
     * @param name The name of the user.
     */
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters

    /**
     * Gets the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() { return name; }

    /**
     * Gets the role of the user.
     *
     * @return The role of the user.
     */
    public String getRole() { return role; }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The ID of the user.
     */
    public String getId() { return id; }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() { return password; }

    /**
     * Gets the blood type of the user.
     *
     * @return The blood type of the user.
     */
    public String getBloodType() { return bloodType; }

    /**
     * Gets the contact information of the user.
     *
     * @return The contact information of the user.
     */
    public String getContactInfo() { return contactInfo; }

    /**
     * Gets the contact number of the user.
     *
     * @return The contact number of the user.
     */
    public int getContactNumber() { return contactNumber; }

    /**
     * Gets the gender of the user.
     *
     * @return The gender of the user.
     */
    public String getGender() { return gender; }

    /**
     * Gets the age of the user.
     *
     * @return The age of the user.
     */
    public int getAge() { return age; }

    /**
     * Gets the date of birth of the user.
     *
     * @return The date of birth of the user.
     */
    public String getDateOfBirth() { return dateOfBirth; }

    // Setters

    /**
     * Sets the unique identifier for the user.
     *
     * @param id The new ID for the user.
     */
    public void setId(String id) { this.id = id; }

    /**
     * Sets the name of the user.
     *
     * @param name The new name for the user.
     */
    public void setName(String name) { this.name = name; }

    /**
     * Sets the password for the user account.
     *
     * @param password The new password for the user.
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Sets the gender of the user.
     *
     * @param gender The new gender for the user.
     */
    public void setGender(String gender) { this.gender = gender; }

    /**
     * Sets the age of the user.
     *
     * @param age The new age for the user.
     */
    public void setAge(int age) { this.age = age; }

    /**
     * Sets the new user status.
     *
     * @param newUser  Indicates if the user is a new user (1 for yes, 0 for no).
     */
    public void setNewUser (int newUser ) { this.newUser  = newUser ; }

    /**
     * Sets the contact number of the user.
     *
     * @param contactNumber The new contact number for the user.
     */
    public void setContactNumber(int contactNumber) { this.contactNumber = contactNumber; }

    /**
     * Sets the blood type of the user.
     *
     * @param bloodType The new blood type for the user.
     */
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    /**
     * Sets the contact information of the user.
     *
     * @param contactInfo The new contact information for the user.
     */
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    /**
     * Checks if the user is a new user.
     *
     * @return true if the user is new, false otherwise.
     */
    public boolean isNewUser () {
        return newUser  == 1;
    }

    /**
     * Validates the input password against the stored password.
     *
     * @param inputPassword The password to validate.
     * @return true if the input password matches the stored password, false otherwise.
     */
    public boolean validatePassword(String inputPassword) {
        return password.equals(inputPassword.trim());
    }

    /**
     * Changes the password for the user account.
     *
     * @param newPassword The new password to set for the user.
     */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return A string containing the user's details.
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Role: " + role + ", Gender: " + gender +
            ", Age: " + age + ", Date of Birth: " + dateOfBirth + ", Blood Type: " + bloodType +
            ", Contact Info: " + contactInfo + ", Contact Number: " + contactNumber + ", New User: " + newUser ;
    }
}