# easyBook

A vacation booking app, that allows users to select dates, a resort, which room they would like, and optional activities to add to their trip (which change based on resort). The user enters their personal information, views a summary of their selections, and is given an option to edit or confirm their trip, presenting them with an option to save a ["receipt"](Czerwik_Syed_project/build/classes/ResortBooking/Receipt.class). Resorts, rooms, and activities created in a [factory class](Czerwik_Syed_project/build/classes/ResortBooking/ResortCreator.class).

I used a [Random Access File](Czerwik_Syed_project/build/classes/ResortBooking/UserLoginInfo.class) to store 2 preset accounts (username:John, password:101 and username:Jane, password:102), to show that theoretically the user would be able to log in when presented with the user information form, in order to autofill their information. This would not allow a new user to store their information once the app closes however, so a new approach will be needed. 
[click here for main class to get started](Czerwik_Syed_project/build/classes/EasyBook/EasyBook.class)



Contributions (javafx, user information form, calendar): Shaheer Syed

Maintenance: Martha Czerwik

While the project was submitted for grading, there are still a few bugs to fix, as well as overall code updating to be done. The following are some general updates I have done or am working on:

- [x] User information form: fix regex for postal code, credit card info, address
- [x] User information form: prevent form from submitting unless all fields are valid 
- [x] User information form: remove “valid” label from empty cells, fix invalid label for address
- [x] Bedroom selection – change alert window to confirmation
- [ ] User information form: Put into method/loops (too much repetitive code)
- [ ] JavaFX: put into separate classes, add loops (repetitive code)
- [ ] JavaFX/General appearance: window sizes made the same/fixed, center alert windows?, continue adding styling to pages without any
- [ ] Check-in dates: fix to not allow past dates 
- [ ] User information form: confusing regarding login - add separate section for logging in with more details/make an account (RAF file will not save though..JSON?)
- [ ] RAF: adjust lengths of variable input
