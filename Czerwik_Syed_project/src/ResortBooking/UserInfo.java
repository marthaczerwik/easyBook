
package ResortBooking;

/**
 * UserInfo class holds all of the user's personal information that gets inputted
 * on the form; creates an object that will be used to display a summary of the
 * user's personal information
 * @author Martha Czerwik
 */
public class UserInfo {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String zipcode;
    private String province;
    private String city;
    private String country;
    private String cardNumber;
    private String cardHolderFN;
    private String cardHolderLN;

    public UserInfo() {
    }
    
    public UserInfo(String firstName, String lastName, String email, String phone, String address, String zipcode, String province, String city, String country, String cardNumber, String cardHolderFN, String cardHolderLN, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.zipcode = zipcode;
        this.province = province;
        this.city = city;
        this.country = country;
        this.cardNumber = cardNumber;
        this.cardHolderFN = cardHolderFN;
        this.cardHolderLN = cardHolderLN;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderFN() {
        return cardHolderFN;
    }

    public void setCardHolderFN(String cardHolderFN) {
        this.cardHolderFN = cardHolderFN;
    }

    public String getCardHolderLN() {
        return cardHolderLN;
    }

    public void setCardHolderLN(String cardHolderLN) {
        this.cardHolderLN = cardHolderLN;
    }

    @Override
    public String toString() {
        return "Client Information:\n\nFirst Name: "+ firstName + "\nLast Name: " + lastName + 
                "\nEmail: " + email + "\nPhone: " + phone + "\nAddress: " + 
                address + "\nCity: " + city + "\nProvince: " + province + 
                "\nPostal or Zipcode: " + zipcode  + "\nCountry: " + country + 
                 "\nCard Number: " + cardNumber 
                + "\nCard Holder First Name: " + cardHolderFN + "\nCard Holder Last Name: " + cardHolderLN;
    }
    
}
