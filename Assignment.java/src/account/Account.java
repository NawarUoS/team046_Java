package src.account;

import javax.print.attribute.HashPrintJobAttributeSet;
import src.util.*;
import java.util.*;

public class Account {
    private String userID;
    private List<UserRole> userRoles;
    private String emailAddress;
    private String password;
    private String forename;
    private String surname;

    // Constructor
    public Account(String userID, List<UserRole> userRoles, String email,
                   String password, String forename, String surname) {
        this.userID = userID;
        this.userRoles = userRoles;
        this.emailAddress = email;
        this.password = password;
        this.forename = forename;
        this.surname = surname;
    }

    // randomly generated userID
    public Account(List<UserRole> userRoles, String email, String password,
                   String forename, String surname) {
        this.userID = UniqueUserIDGenerator.generateUniqueUserID();
        this.userRoles = userRoles;
        this.emailAddress = email;
        this.password = password;
        this.forename = forename;
        this.surname = surname;
    }

    // ManagerService methods
    public void addUserRole(UserRole userRole) {
        List<UserRole> newUserRoles = getUserRoles();
        newUserRoles.add(userRole);
        this.userRoles = newUserRoles;
    }

    public void removeUserRole(UserRole userRole) {
        List<UserRole> newUserRoles = getUserRoles();
        newUserRoles.remove(userRole);
        this.userRoles = newUserRoles;
    }

    // Getter methods
    public String getUserID() {
        return userID;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public int getUserCustomer() {
        if (userRoles.contains(UserRole.CUSTOMER))
            return 1;
        else
            return 0;
    }

    public int getUserStaff() {
        if (userRoles.contains(UserRole.STAFF))
            return 1;
        else
            return 0;
    }

    public int getUserManager() {
        if (userRoles.contains(UserRole.MANAGER))
            return 1;
        else
            return 0;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordHash() {
        return HashedPasswordGenerator.hashPassword(password.toCharArray(),
                userID);
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }


    // Setter methods
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    // toString
    public String toString() {
        return "User ID: " + userID + "\n" +
                "User: " + forename + " " + surname + "\n" +
                "Email: " + emailAddress + "\n" +
                "Password: " + password + "\n" +
                "Roles: " + userRoles;
    }
}
