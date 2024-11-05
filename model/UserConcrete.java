package model;

import java.io.Serializable;


public class UserConcrete implements Serializable {
    /** The full name of the user. */
    //should it be private or public?
    protected String name;

    /** The unique NTU network user ID of the user. */
    //should it be private or public?
    protected String userID;

    /** The user's password, which defaults to "password". */
    private String password;


    public UserConcrete(String name, String userID) {
        this.name = name;
        this.userID = userID;
        this.password = "password";
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    /* - if we need later 
    /**
     * Sets or updates the user's password.
     * 
     * @param password The new password for the user as a String.
     */
    /*public void setPassword(String password) {
        this.password = password;
    }
    

    /** - see if need later 
     * Returns a string representation of the user, including their name, user ID,
     * and faculty.
     * 
     * @return A string representation of the user.
     */
    /*@Override
    public String toString() {
        return this.name + " " + this.userID }
    */
}


