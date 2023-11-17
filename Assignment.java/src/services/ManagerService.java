package src.services;

import src.account.*;

public class ManagerService {
    private int currentUserID;

    public void promoteUsersToStaff(int[] userIDs) {
        for (int userID : userIDs) {
            promoteUserToStaff(userID);
        }
    }

    public void promoteUserToStaff(int userID) {
        Account currentUser = Account.getUserByID(currentUserID);
        if (currentUser.getUserRoles().contains(UserRole.MANAGER)) {
            Account.getUserByID(userID).addUserRole(UserRole.STAFF);
        }
    }

    public void dismissUserFromStaff(int userID) {
        Account currentUser = Account.getUserByID(currentUserID);
        if (currentUser.getUserRoles().contains(UserRole.MANAGER)) {
            Account.getUserByID(userID).removeUserRole(UserRole.STAFF);
        }
    }
}
