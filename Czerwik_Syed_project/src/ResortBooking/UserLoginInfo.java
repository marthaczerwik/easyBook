package ResortBooking;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * The UserLoginInfo class allows users that have logins to store their personal
 * information, so that when they need to enter their information on the form,
 * they can login to auto-complete the form with their saved information; the
 * users who have their information stored have the following
 * username/passwords: john 101, jane 102, mark 103
 *
 * @author Martha Czerwik
 */
public class UserLoginInfo {

    //FIELDS FOR USER INFO SUMMARY
    private static final int FIRST_NAME_CHARACTERS = 15;
    private static final int FIRST_NAME_SIZE = FIRST_NAME_CHARACTERS * 2;
    private static final int LAST_NAME_CHARACTERS = 15;
    private static final int LAST_NAME_SIZE = LAST_NAME_CHARACTERS * 2;
    private static final int EMAIL_CHARACTERS = 30;
    private static final int EMAIL_SIZE = EMAIL_CHARACTERS * 2;
    private static final int PHONE_CHARS = 10;
    private static final int PHONE_SIZE = PHONE_CHARS * 2;
    private static final int ADDRESS_CHARS = 30;
    private static final int ADDRESS_SIZE = ADDRESS_CHARS * 2;
    private static final int ZIP_CHARS = 10;
    private static final int ZIP_SIZE = ZIP_CHARS * 2;
    private static final int CITY_CHARS = 15;
    private static final int CITY_SIZE = CITY_CHARS * 2;
    private static final int COUNTRY_CHARS = 15;
    private static final int COUNTRY_SIZE = COUNTRY_CHARS * 2;
    private static final int PROVINCE_CHARS = 15;
    private static final int PROVINCE_SIZE = PROVINCE_CHARS * 2;
    private static final int CARDHOLDER_FIRST_CHARS = 15;
    private static final int CARDHOLDER_FIRST_SIZE = CARDHOLDER_FIRST_CHARS * 2;
    private static final int CARDHOLDER_LAST_CHARS = 15;
    private static final int CARDHOLDER_LAST_SIZE = CARDHOLDER_LAST_CHARS * 2;
    private static final int CARDNUMBER_CHARS = 15;
    private static final int CARDNUMBER_SIZE = CARDNUMBER_CHARS * 2;
    private static final int USER_INFO_SIZE = (FIRST_NAME_SIZE + LAST_NAME_SIZE
            + EMAIL_SIZE + PHONE_SIZE + ADDRESS_SIZE + ZIP_SIZE + CITY_SIZE + COUNTRY_SIZE
            + PROVINCE_SIZE + +CARDHOLDER_FIRST_SIZE
            + CARDHOLDER_LAST_SIZE + CARDNUMBER_SIZE);

    String[] firstNames = {"John", "Jane", "Mark"};
    String[] lastNames = {"Smith", "Brown", "McIntosh"};
    String[] emails = {"john@gmail.com", "jane_b@hotmail.com", "markm@gmail.com"};
    String[] phoneNumbers = {"905-964-4693", "4165557346", "(647) 123 1234"};
    String[] addresses = {"1234 Road St", "67 Road Avenue", "100 Road Crescent"};
    String[] zipcodes = {"L6L 2T4", "M7M 4Y6", "L4B 5B7"};
    String[] provinces = {"Ontario", "Quebec", "Nova Scotia"};
    String[] cities = {"Mississauga", "Montreal", "Halifax"};
    String[] countries = {"Canada", "Canada", "Canada"};
    String[] cardholderFNs = {"John", "Greg", "Mark"};
    String[] cardholderLNs = {"Smith", "Brown", "McIntosh"};
    String[] cardNumbers = {"000013691467146", "93769384582947", "000385729581056"};

    UserInfo userinfo = new UserInfo();
    int user;

    /**
     * Method that creates the RAF based on provided arrays of users with preset
     * information; if the user logs in, the information from the RAF is passed
     * to the form to autocomplete it; clicking on login also will show a new
     * button that allows them to modify their information
     *
     * @param userinfo
     * @param username
     * @param password
     * @param firstIn
     * @param lastIn
     * @param emailIn
     * @param phoneIn
     * @param addressIn
     * @param zipIn
     * @param provinceIn
     * @param cityIn
     * @param countryIn
     * @param cardHolderFirst
     * @param cardHolderLast
     * @param cardInfo
     * @param modifyButton
     */
    public void createRaf(UserInfo userinfo, String username, String password, TextField firstIn,
            TextField lastIn, TextField emailIn, TextField phoneIn, TextField addressIn,
            TextField zipIn, TextField provinceIn, TextField cityIn,
            TextField countryIn, TextField cardHolderFirst, TextField cardHolderLast, TextField cardInfo, Button modifyButton) {

        try (RandomAccessFile login = new RandomAccessFile("login.dat", "rw")) {
            //delete old contents
            login.setLength(0);

            for (int i = 0; i < firstNames.length; i++) {
                login.writeChars(checkString((firstNames[i]), FIRST_NAME_CHARACTERS));
                login.writeChars(checkString((lastNames[i]), LAST_NAME_CHARACTERS));
                login.writeChars(checkString((emails[i]), EMAIL_CHARACTERS));
                login.writeChars(checkString((phoneNumbers[i]), PHONE_CHARS));
                login.writeChars(checkString((addresses[i]), ADDRESS_CHARS));
                login.writeChars(checkString((zipcodes[i]), ZIP_CHARS));
                login.writeChars(checkString((cities[i]), CITY_CHARS));
                login.writeChars(checkString((countries[i]), COUNTRY_CHARS));
                login.writeChars(checkString((provinces[i]), PROVINCE_CHARS));
                login.writeChars(checkString((cardholderFNs[i]), CARDHOLDER_FIRST_CHARS));
                login.writeChars(checkString((cardholderLNs[i]), CARDHOLDER_LAST_CHARS));
                login.writeChars(checkString((cardNumbers[i]), CARDNUMBER_CHARS));

            }
            login.close(); //release resources
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

        //read the file
        try (RandomAccessFile login = new RandomAccessFile("login.dat", "r")) {
            if (username.equals("john") && password.equals("101")) {
                user = 0;
            } else if (username.equals("jane") && password.equals("102")) {
                user = 1;
            } else if (username.equals("mark") && password.equals("103")) {
                user = 2;
            }
            readRaf(user, login, firstIn, lastIn, emailIn, phoneIn, addressIn,
                    zipIn, provinceIn, cityIn, countryIn, cardHolderFirst, cardHolderLast, cardInfo);

            modifyButton.setOnAction(e -> modify(user, firstIn, lastIn, emailIn, phoneIn, addressIn,
                    zipIn, provinceIn, cityIn, countryIn, cardHolderFirst, cardHolderLast, cardInfo));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

    }

    /**
     * Method to check the length of a string in the RAF; if it is too long, it
     * is truncated, if it is too short, it calls on a method to add padding
     *
     * @param string
     * @param varChars
     * @return
     */
    private static String checkString(String string, int varChars) {
        //check if name needs to be truncated or needs padding
        if (string.length() > varChars) {
            string = string.substring(0, varChars);
        } else if (string.length() <= varChars) {
            string += getPadding(string.length(), varChars);
        }
        return string;
    }

    /**
     * Method to add padding to strings that are too short to fit the RAF
     * specified size
     *
     * @param currentSize
     * @param varChars
     * @return
     */
    private static String getPadding(int currentSize, int varChars) {
        //adding padding to the name
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < varChars - currentSize; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    /**
     * Method to modify user information; eg, if the user's address has changed
     * and they want the new address to be reflected in future autocompleted
     * forms, they would type in the new address and click modify to update the
     * RAF
     *
     * @param user
     * @param firstIn
     * @param lastIn
     * @param emailIn
     * @param phoneIn
     * @param addressIn
     * @param zipIn
     * @param provinceIn
     * @param cityIn
     * @param countryIn
     * @param cardHolderFirst
     * @param cardHolderLast
     * @param cardInfo
     */
    public void modify(int user, TextField firstIn,
            TextField lastIn, TextField emailIn, TextField phoneIn, TextField addressIn,
            TextField zipIn, TextField provinceIn, TextField cityIn,
            TextField countryIn, TextField cardHolderFirst, TextField cardHolderLast, TextField cardInfo) {
        try (RandomAccessFile login = new RandomAccessFile("login.dat", "rw")) {
            login.seek(USER_INFO_SIZE * user);
            login.writeChars(firstIn.getText());

            login.seek(USER_INFO_SIZE * user);
            login.skipBytes(FIRST_NAME_SIZE);
            login.writeChars(lastIn.getText());

            login.seek(USER_INFO_SIZE * user);
            login.skipBytes((FIRST_NAME_SIZE) + (LAST_NAME_SIZE));
            login.writeChars(emailIn.getText());

            login.seek(USER_INFO_SIZE * user);
            login.skipBytes((FIRST_NAME_SIZE) + (LAST_NAME_SIZE) + (EMAIL_SIZE));
            login.writeChars(phoneIn.getText());

            login.seek(USER_INFO_SIZE * user);
            login.skipBytes((FIRST_NAME_SIZE) + (LAST_NAME_SIZE) + (EMAIL_SIZE) + (PHONE_SIZE)
            );
            login.writeChars(addressIn.getText());

            login.seek(USER_INFO_SIZE * user);
            login.skipBytes((FIRST_NAME_SIZE) + (LAST_NAME_SIZE) + (EMAIL_SIZE) + (PHONE_SIZE)
                    + (ADDRESS_SIZE));
            login.writeChars(zipIn.getText());

            login.seek(USER_INFO_SIZE * user);
            login.skipBytes((FIRST_NAME_SIZE) + (LAST_NAME_SIZE) + (EMAIL_SIZE) + (PHONE_SIZE)
                    + (ADDRESS_SIZE) + (ZIP_SIZE));
            login.writeChars(cityIn.getText());

            login.seek(USER_INFO_SIZE * user);
            login.skipBytes((FIRST_NAME_SIZE) + (LAST_NAME_SIZE) + (EMAIL_SIZE) + (PHONE_SIZE)
                    + (ADDRESS_SIZE) + (ZIP_SIZE) + (CITY_SIZE));
            login.writeChars(countryIn.getText());

            login.seek(USER_INFO_SIZE * user);
            login.skipBytes((FIRST_NAME_SIZE) + (LAST_NAME_SIZE) + (EMAIL_SIZE) + (PHONE_SIZE)
                    + (ADDRESS_SIZE) + (ZIP_SIZE) + (CITY_SIZE) + (COUNTRY_SIZE));
            login.writeChars(provinceIn.getText());

            login.seek(USER_INFO_SIZE * user);
            login.skipBytes((FIRST_NAME_SIZE) + (LAST_NAME_SIZE) + (EMAIL_SIZE) + (PHONE_SIZE)
                    + (ADDRESS_SIZE) + (ZIP_SIZE) + (CITY_SIZE) + (COUNTRY_SIZE) + (PROVINCE_SIZE)
            );
            login.writeChars(cardHolderFirst.getText());

            login.seek(USER_INFO_SIZE * user);
            login.skipBytes((FIRST_NAME_SIZE) + (LAST_NAME_SIZE) + (EMAIL_SIZE) + (PHONE_SIZE)
                    + (ADDRESS_SIZE) + (ZIP_SIZE) + (CITY_SIZE) + (COUNTRY_SIZE) + (PROVINCE_SIZE)
                    + (CARDHOLDER_FIRST_SIZE));
            login.writeChars(cardHolderLast.getText());

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

    }

    /**
     * Method to read the RAF
     *
     * @param user
     * @param login
     * @param firstIn
     * @param lastIn
     * @param emailIn
     * @param phoneIn
     * @param addressIn
     * @param zipIn
     * @param provinceIn
     * @param cityIn
     * @param countryIn
     * @param cardHolderFirst
     * @param cardHolderLast
     * @param cardInfo
     */
    private void readRaf(int user, RandomAccessFile login, TextField firstIn,
            TextField lastIn, TextField emailIn, TextField phoneIn, TextField addressIn,
            TextField zipIn, TextField provinceIn, TextField cityIn,
            TextField countryIn, TextField cardHolderFirst, TextField cardHolderLast, TextField cardInfo) {
        try {
            login.seek(USER_INFO_SIZE * user);

            //print first name
            StringBuilder firstname = new StringBuilder();
            for (int i = 0; i < FIRST_NAME_CHARACTERS; i++) {
                firstname.append(login.readChar());
            }
            firstIn.setText(firstname.toString());

            //print last name
            login.seek(USER_INFO_SIZE * user);
            login.skipBytes(FIRST_NAME_SIZE);

            StringBuilder lastname = new StringBuilder();
            for (int i = 0; i < LAST_NAME_CHARACTERS; i++) {
                lastname.append(login.readChar());
            }
            lastIn.setText(lastname.toString());

            //print email
            login.seek(USER_INFO_SIZE * user);
            login.skipBytes(FIRST_NAME_SIZE + LAST_NAME_SIZE);

            StringBuilder email = new StringBuilder();
            for (int i = 0; i < EMAIL_CHARACTERS; i++) {
                email.append(login.readChar());
            }
            emailIn.setText(email.toString());

            //print phone
            login.seek(USER_INFO_SIZE * user);
            login.skipBytes(FIRST_NAME_SIZE + LAST_NAME_SIZE + EMAIL_SIZE);

            StringBuilder phone = new StringBuilder();
            for (int i = 0; i < PHONE_CHARS; i++) {
                phone.append(login.readChar());
            }
            phoneIn.setText(phone.toString());

            //print address
            login.seek(USER_INFO_SIZE * user);
            login.skipBytes(FIRST_NAME_SIZE + LAST_NAME_SIZE + EMAIL_SIZE + PHONE_SIZE);

            StringBuilder address = new StringBuilder();
            for (int i = 0; i < ADDRESS_CHARS; i++) {
                address.append(login.readChar());
            }
            addressIn.setText(address.toString());

            //print zipcode
            login.seek(USER_INFO_SIZE * user);
            login.skipBytes(FIRST_NAME_SIZE + LAST_NAME_SIZE + EMAIL_SIZE
                    + PHONE_SIZE + ADDRESS_SIZE);

            StringBuilder zip = new StringBuilder();
            for (int i = 0; i < ZIP_CHARS; i++) {
                zip.append(login.readChar());
            }
            zipIn.setText(zip.toString());

            //print city
            login.seek(USER_INFO_SIZE * user);
            login.skipBytes(FIRST_NAME_SIZE + LAST_NAME_SIZE + EMAIL_SIZE
                    + PHONE_SIZE + ADDRESS_SIZE + ZIP_SIZE);

            StringBuilder city = new StringBuilder();
            for (int i = 0; i < CITY_CHARS; i++) {
                city.append(login.readChar());
            }
            cityIn.setText(city.toString());

            //print country
            login.seek(USER_INFO_SIZE * user);
            login.skipBytes(FIRST_NAME_SIZE + LAST_NAME_SIZE + EMAIL_SIZE
                    + PHONE_SIZE + ADDRESS_SIZE + ZIP_SIZE + CITY_SIZE);

            StringBuilder country = new StringBuilder();
            for (int i = 0; i < COUNTRY_CHARS; i++) {
                country.append(login.readChar());
            }
            countryIn.setText(country.toString());

            //print province
            login.seek(USER_INFO_SIZE * user);
            login.skipBytes(FIRST_NAME_SIZE + LAST_NAME_SIZE + EMAIL_SIZE
                    + PHONE_SIZE + ADDRESS_SIZE + ZIP_SIZE + CITY_SIZE + COUNTRY_SIZE);

            StringBuilder province = new StringBuilder();
            for (int i = 0; i < PROVINCE_CHARS; i++) {
                province.append(login.readChar());
            }
            provinceIn.setText(province.toString());

            //print CARDHOLDER FIRST NAME
            login.seek(USER_INFO_SIZE * user);
            login.skipBytes(FIRST_NAME_SIZE + LAST_NAME_SIZE + EMAIL_SIZE
                    + PHONE_SIZE + ADDRESS_SIZE + ZIP_SIZE + CITY_SIZE + COUNTRY_SIZE
                    + PROVINCE_SIZE);

            StringBuilder cardholderFN = new StringBuilder();
            for (int i = 0; i < CARDHOLDER_FIRST_CHARS; i++) {
                cardholderFN.append(login.readChar());
            }
            cardHolderFirst.setText(cardholderFN.toString());

            //print CARDHOLDER last NAME
            login.seek(USER_INFO_SIZE * user);
            login.skipBytes(FIRST_NAME_SIZE + LAST_NAME_SIZE + EMAIL_SIZE
                    + PHONE_SIZE + ADDRESS_SIZE + ZIP_SIZE + CITY_SIZE + COUNTRY_SIZE
                    + PROVINCE_SIZE + CARDHOLDER_FIRST_SIZE);

            StringBuilder cardholderLN = new StringBuilder();
            for (int i = 0; i < CARDHOLDER_LAST_CHARS; i++) {
                cardholderLN.append(login.readChar());
            }
            cardHolderLast.setText(cardholderLN.toString());

            //print cardnumber
            login.seek(USER_INFO_SIZE * user);
            login.skipBytes(FIRST_NAME_SIZE + LAST_NAME_SIZE + EMAIL_SIZE
                    + PHONE_SIZE + ADDRESS_SIZE + ZIP_SIZE + CITY_SIZE + COUNTRY_SIZE
                    + PROVINCE_SIZE + CARDHOLDER_FIRST_SIZE + CARDHOLDER_LAST_SIZE);

            StringBuilder cardNumber = new StringBuilder();
            for (int i = 0; i < CARDNUMBER_CHARS; i++) {
                cardNumber.append(login.readChar());
            }
            cardInfo.setText(cardNumber.toString());

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

}
