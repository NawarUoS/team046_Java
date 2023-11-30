package src.util;

import src.account.Account;
import src.account.Address;
import src.model.AddressOperations;

public class CurrentUserCache {
    private static Account userLoggedIn;

    public static void storeUserInformation(Account account) {
        userLoggedIn = account;
    }

    public static Account getLoggedInUser() {
        return userLoggedIn;
    }

    public static void clearCache() {
        userLoggedIn = null;
    }
}
