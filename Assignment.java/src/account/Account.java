package src.account;

import javax.print.attribute.HashPrintJobAttributeSet;
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

    // Class methods
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

    public static Account getUserByID(String userID) {
        // for now - implement later after going over SQL
        return new Account(userID, List.of(UserRole.CUSTOMER), "emem",
                "jdj", "jjd", "jdjd");
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
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

}
