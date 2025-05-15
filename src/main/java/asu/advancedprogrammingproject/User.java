/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asu.advancedprogrammingproject;

/**
 *
 * @author nabil
 */
public abstract class User {
    protected String name;
    protected int ID;
    protected String password;
    protected boolean loggedIn;

    User(){
        this.name = " ";
        this.ID = 0;
        this.password = " ";
        this.loggedIn = false;     
    }

    User(String name , int ID , String password) {
        this.name = name ; 
        this.ID = ID;
        this.password = password;
        this.loggedIn = false;
    }

    public void logIn(int ID, String password) throws AlreadyLoggedInException {
        if (loggedIn) {
            throw new AlreadyLoggedInException("User is already logged in.");
        }
        if (this.password.equals(password) && this.ID == ID) {
            System.out.println("Logged in successfully.");
            loggedIn = true;
        } else {
            System.out.println("Incorrect ID or password.");
        }
    }
    
    public void logout() throws AlreadyLoggedOutException {
        if (loggedIn) {
            loggedIn = false;
            System.out.println("Logged out successfully.");
        } else {
            throw new AlreadyLoggedOutException("User is already logged out.");
        }
    }
    @Override
public String toString() {
    return "User{name='" + name + "', ID=" + ID + ", loggedIn=" + loggedIn + "}";
}
    public abstract void getRole();
       
}
