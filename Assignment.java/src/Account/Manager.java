package src.account;

import java.util.List;

public class Manager extends Staff {
    public Manager(String email, String password, String forename,
                 String surname, Boolean isCustomer) {
        super(List.of(UserRole.MANAGER, UserRole.STAFF), email, password,
                forename, surname, isCustomer);
    }
}
