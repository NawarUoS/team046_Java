package src.Account;

public class Account {
    private int userID;
    private int[] userRole;
    private String emailAddress;
    private String password;
    private String forename;
    private String surname;

    private Boolean isCustomer;

    //Get methods
    public int getUserID() {
        return userID;
    }

    public int[] getUserRole() {
        return userRole;
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

    public Boolean getCustomer() {
        return isCustomer;
    }
}
