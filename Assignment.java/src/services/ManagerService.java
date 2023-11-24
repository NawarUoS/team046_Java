package src.services;

import src.account.*;

public class ManagerService extends Service {
    private String currentUserID;

    public void promoteUsersToStaff(String[] userIDs) {
        for (String userID : userIDs) {
            promoteUserToStaff(userID);
        }
    }

    public void promoteUserToStaff(String userID) {
        Account currentUser = Account.getUserByID(getCurrentUserID());
        if (currentUser.getUserRoles().contains(UserRole.MANAGER)) {
            Account.getUserByID(userID).addUserRole(UserRole.STAFF);
        }
        // else: add exception later or error message
    }

    public void dismissUserFromStaff(String userID) {
        Account currentUser = Account.getUserByID(getCurrentUserID());
        if (currentUser.getUserRoles().contains(UserRole.MANAGER)) {
            Account.getUserByID(userID).removeUserRole(UserRole.STAFF);
        }
        // else: add exception later or error message
    }
}
