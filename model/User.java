package model;

public abstract class User {
    private String userID;
    private String name;
    private String password;

    public User(String userID, String name) {
        this.userID = userID;
        this.name = name;
        this.password = "password"; // Default password
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public boolean validatePassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // Abstract method for menu display; subclasses implement their specific menus
    public abstract void displayMenu();
}
