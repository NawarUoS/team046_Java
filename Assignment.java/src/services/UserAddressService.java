package src.services;

import src.account.*;

public class UserAddressService extends Service {
    private int currentUserID;

    public void updateAddress(int houseNumber, String streetName,
                              String cityName, String postCode) {
        Account currentUser = Account.getUserByID(currentUserID);
    }
}
