package model;

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
    private int newUser;

    public User(String id, String name, String role, String gender, int age, String dateOfBirth, 
                 String bloodType, String contactInfo, String password, int newUser) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.password = password;
        this.newUser = newUser;
    }

        // Getters and any other methods needed by Staff
        public String getName() { return name; }
        public String getRole() { return role; }
        public String getId() { return id; }
        public String getPassword() { return password; }
        public String getBloodType() { return bloodType; }
        public String getContactInfo() { return contactInfo; }
        public void setNewUser(int newUser) { this.newUser = newUser; }
    
        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Role: " + role + ", Gender: " + gender +
                   ", Age: " + age + ", Date of Birth: " + dateOfBirth + ", Blood Type: " + bloodType +
                   ", Contact Info: " + contactInfo + ", New User: " + newUser;
        }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isNewUser() {
        return newUser == 1;
    }

    public boolean validatePassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

}
