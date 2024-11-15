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
        private int contactNumber;
        private int newUser;

        public User(String id, String name, String role, String gender, int age, String dateOfBirth, 
                    String bloodType, String contactInfo, String password, int newUser, int contactNumber) {
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
            this.contactNumber = contactNumber;
        }

        // Overloaded constructor
        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        // Getters
        public String getName() { return name; }
        public String getRole() { return role; }
        public String getId() { return id; }
        public String getPassword() { return password; }
        public String getBloodType() { return bloodType; }
        public String getContactInfo() { return contactInfo; }
        public int getContactNumber() { return contactNumber; }
        public String getGender() { return gender; }
        public int getAge() { return age; }
        public String getDateOfBirth() { return dateOfBirth; } // Added getter

        // Setters
        public void setId(String id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setPassword(String password) { this.password = password; }
        public void setGender(String gender) { this.gender = gender; }
        public void setAge(int age) { this.age = age; }
        public void setNewUser(int newUser) { this.newUser = newUser; }
        public void setContactNumber(int contactNumber) { this.contactNumber = contactNumber; }
        public void setBloodType(String bloodType) { this.bloodType = bloodType; }
        public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
        
        // Method to check if the user is new
        public boolean isNewUser() {
            return newUser == 1;
        }   

    // In User class
        public boolean validatePassword(String inputPassword) {
            System.out.println("Debug: Stored Password: [" + password + "]");
            System.out.println("Debug: Input Password: [" + inputPassword.trim() + "]");
            return password.equals(inputPassword.trim());
        }


        // Change password
        public void changePassword(String newPassword) {
            this.password = newPassword;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Role: " + role + ", Gender: " + gender +
                ", Age: " + age + ", Date of Birth: " + dateOfBirth + ", Blood Type: " + bloodType +
                ", Contact Info: " + contactInfo + ", Contact Number: " + contactNumber + ", New User: " + newUser;
        }
    }
