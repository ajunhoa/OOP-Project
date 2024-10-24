abstract class User {
    protected String userID;
    protected String password;
    protected String name;
    
    public User(String userID, String name) {
        this.userID = userID;
        this.name = name;
        this.password = "password"; // Default password
    }

    public boolean login(String userID, String password) {
        return this.userID.equals(userID) && this.password.equals(password);
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }
    
    public abstract void displayMenu();
}
