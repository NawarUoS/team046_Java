package src.account;

import src.util.*;
import java.util.*;

public class Account {
    private String userID;
    private List<UserRole> userRoles;
    private String emailAddress;
    private String passwordHash;
    private String forename;
    private String surname;

    // Constructor
    public Account(String userID, List<UserRole> userRoles, String email,
                   char[] password, String forename, String surname) {
        this.userID = userID;
        this.userRoles = userRoles;
        this.emailAddress = email;
        this.passwordHash = HashedPasswordGenerator.hashPassword(password, userID);
        this.forename = forename;
        this.surname = surname;
    }

    // randomly generated userID
    public Account(List<UserRole> userRoles, String email, char[] password,
                   String forename, String surname) {
        this.userID = UniqueUserIDGenerator.generateUniqueUserID();
        this.userRoles = userRoles;
        this.emailAddress = email;
        this.passwordHash = HashedPasswordGenerator.hashPassword(password, userID);
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

    public int isCustomer() {
        if (userRoles.contains(UserRole.CUSTOMER))
            return 1;
        else
            return 0;
    }

    public int isStaff() {
        if (userRoles.contains(UserRole.STAFF))
            return 1;
        else
            return 0;
    }

    public int isManager() {
        if (userRoles.contains(UserRole.MANAGER))
            return 1;
        else
            return 0;
    }

    public String getEmailAddress() {
        return emailAddress;
    }


    public String getPasswordHash() {
        return passwordHash;
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

    public void setPassword(char[] password) {
        this.passwordHash =
                HashedPasswordGenerator.hashPassword(password, getUserID());
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
                "Hashed password: " + new String(passwordHash) + "\n" +
                "Roles: " + userRoles;
    }
}
