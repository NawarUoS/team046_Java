package src.account;

public class Address {
    private String userID;
    private int houseNumber;
    private String streetName;
    private String cityName;
    private String postCode;

    // Constructor
    public Address(String userID, int houseNumber, String streetName,
                   String cityName, String postCode) {
        this.userID = userID;
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.cityName = cityName;
        this.postCode = postCode;
    }

    // Getter methods
    public String getUserID() { return userID; }
    public int getHouseNumber() {
        return houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getPostCode() {
        return postCode;
    }

    // Setter methods
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
