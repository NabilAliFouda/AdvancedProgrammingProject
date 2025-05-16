/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asu.advancedprogrammingproject;

/**
 *
 * @author nabil
 */
/**
 * Abstract base class representing a general user in the system.
 * Contains shared fields and functionality for login and logout logic.
 */
public abstract class User {
    // User information fields accessible to subclasses
    protected String name;
    protected int ID;
    protected String password;
    protected boolean loggedIn;

    /**
     * Default constructor - initializes user with placeholder values.
     */
    User() {
        this.name = " ";
        this.ID = 0;
        this.password = " ";
        this.loggedIn = false;     
    }

    /**
     * Parameterized constructor - initializes user with provided values.
     * @param name      the name of the user
     * @param ID        the user's unique ID
     * @param password  the user's password
     */
    User(String name, int ID, String password) {
        this.name = name; 
        this.ID = ID;
        this.password = password;
        this.loggedIn = false;
    }

    /**
     * Attempts to log in the user using the provided credentials.
     * @param ID        the input ID to validate
     * @param password  the input password to validate
     * @throws AlreadyLoggedInException if the user is already logged in
     */
    public void logIn(int ID, String password) throws AlreadyLoggedInException, IllegalArgumentException {
        if (loggedIn) {
            throw new AlreadyLoggedInException("User is already logged in.");
        }
        if (this.password.equals(password) && this.ID == ID) {
            System.out.println("Logged in successfully.");
            loggedIn = true;
        } else {
            throw new IllegalArgumentException("Incorrect ID or password.");
        }
    }


    /**
     * Logs out the user if they are currently logged in.
     * @throws AlreadyLoggedOutException if the user is already logged out
     */
    public void logout() throws AlreadyLoggedOutException {
        if (loggedIn) {
            loggedIn = false;
            System.out.println("Logged out successfully.");
        } else {
            throw new AlreadyLoggedOutException("User is already logged out.");
        }
    }

    /**
     * Returns a string representation of the user.
     * Useful for debugging or displaying user info.
     */
    public String getName(){
        return name;
    }    
        
    public int getID(){
        return ID;
    }    
 
    public String getPassword(){
        return password;
    } 
    
    @Override
    public String toString() {
        return "User{name='" + name + "', ID=" + ID + ", loggedIn=" + loggedIn + "}";
    }

    /**
     * Abstract method that must be implemented by subclasses to return the user's role.
     */

    public abstract void getRole();      
}

