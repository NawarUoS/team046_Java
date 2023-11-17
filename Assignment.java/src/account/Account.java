package src.account;

import javax.print.attribute.HashPrintJobAttributeSet;
import java.util.*;

public class Account {

    public enum UserRole {CUSTOMER, MANAGER, STAFF}
    private int userID;
    private List<UserRole> userRoles;
    private String emailAddress;
    private String password;
    private String forename;
    private String surname;

    // Constructor

    public Account(List<UserRole> userRoles, String email,
                   String password, String forename, String surname) {
        this.userRoles = userRoles;
        this.emailAddress = email;
        this.password = password;
        this.forename = forename;
        this.surname = surname;
    }

    // Get methods
    public int getUserID() {
        return userID;
    }

    public List<UserRole> getUserRole() {
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
}
