package src.account;

import java.util.*;

public class Staff extends Account {
    public Staff(String email, String password, String forename,
                   String surname, Boolean isCustomer) {
        super(List.of(UserRole.STAFF), email, password, forename, surname,
                isCustomer);
    }
    public Staff(List<UserRole> userRoles, String email,
                 String password, String forename, String surname,
                 Boolean isCustomer) {
        super(userRoles, email, password, forename, surname,
                isCustomer);
    }
}
