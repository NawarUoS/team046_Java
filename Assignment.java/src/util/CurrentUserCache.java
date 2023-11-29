package src.util;

import src.account.Account;

public class CurrentUserCache {
    private static Account userLoggedIn;

    public static void storeUserInformation(Account account) {
        userLoggedIn = account;
    }

    public static Account getLoggedInUser() {
        return userLoggedIn;
    }
}
