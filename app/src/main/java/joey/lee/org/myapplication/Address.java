package joey.lee.org.myapplication;

public class Address {

    private String city;
    private String country;
    private int postcode;
    private String street;

    public Address(String city, String country, int postcode, String street) {
        this.city = city;
        this.country = country;
        this.postcode = postcode;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
