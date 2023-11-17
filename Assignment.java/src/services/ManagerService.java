package src.services;

import src.account.*;

public class ManagerService {
    private int currentUserID;
    public void promoteUserToStaff(int userID) {
        Account currentUser = Account.getUserByID(currentUserID);
        if (currentUser.getUserRoles().contains(UserRole.MANAGER)) {
            Account.getUserByID(userID).addUserRole(UserRole.STAFF);
        }
    }
}
