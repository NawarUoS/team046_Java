package src.account;

public class Address {
    private int houseNumber;
    private String streetName;
    private String cityName;
    private String postCode;

    // Constructor
    public Address(int houseNumber, String streetName, String cityName,
                   String postCode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.cityName = cityName;
        this.postCode = postCode;
    }

    //Get methods
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
}
