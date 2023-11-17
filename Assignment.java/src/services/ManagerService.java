package src.services;

import src.account.*;

public class ManagerService {
    private int currentUserID;
    public void promoteUserToStaff(int userID) {
        Account currentUser = Account.getUserByID(currentUserID);
        if (currentUser.getUserRoles().contains(Account.UserRole.MANAGER)) {
            Account.getUserByID(userID).addUserRole(Account.UserRole.STAFF);
        }
    }
}
