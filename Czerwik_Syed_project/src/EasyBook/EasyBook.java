/*
 * easyBook by Marshe Co Ltd
 * A resort booking application that allows users to select a resort, room types and
 * additional activities, and lets them book and pay for their trip, providing
 * them with a receipt that they can save to their device
 * Authors: Martha Czerwik and Shaheer Syed
 */
package EasyBook;

import ResortBooking.Receipt;
import ResortBooking.UserLoginInfo;
import ResortBooking.Resort;
import ResortBooking.ResortActivity;
import ResortBooking.ResortCreator;
import ResortBooking.Room;
import ResortBooking.Trip;
import ResortBooking.UserInfo;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Main class EasyBook provides the user with the interface to book their trip
 * User input will be passed to the Trip constructor in the class Trip; The
 * user's trip summary and their personal information summary will be written to
 * a file in the class Receipt and the user will be able to save their receipt
 * to a file
 *
 * @author Martha Czerwik & Shaheer Syed
 */
public class EasyBook extends Application {

//javafx variables
    RadioButton box3, box2, box1;
    TextArea txtDisplay;
    CheckBox bedBox1, bedBox2, bedBox3, bedBox4, bedBox5, bedBox6, bedBox7, bedBox8, bedBox9;
    Scene scene, scene1, scene2, scene3, scene4, scene5, scene6, scene7;
    TextField firstIn, lastIn, emailIn, phoneIn, addressIn, cityIn, provinceIn,
            countryIn, zipIn, cardInfo, cardHolderFirst, cardHolderLast;
    DatePicker checkInDatePicker;
    DatePicker checkOutDatePicker;
    int a = 550;//horizontal length of scene
    int b = 650;//verical length of scene
    Label tripDetails;
    Label userDetails;
    ListView<ResortActivity> listview = new ListView<>();

//trip info variables
    private int numChildren;
    private int numAdults;
    private int numDays;
    private int userResortChoice = 0;
    private int userRoomChoice;
    private Resort chosenResort;
    private Room roomChoice;
    private double cost;

//arraylist of activities to be passed to Trip constructor
    ArrayList<ResortActivity> activities = new ArrayList<>();
    ResortActivity[] activityArray;
    ArrayList<ResortActivity> activitiesTempList = new ArrayList<>();

//create arraylist of bedrooms to be passed to Trip constructor
    ArrayList<Room> bedrooms = new ArrayList<>();

//arraylist to hold checkboxes of rooms    
    ArrayList<CheckBox> tempRoom = new ArrayList<>();

//instances of classes in order to access
    ResortCreator resort = new ResortCreator();
    Trip newTrip;
    UserInfo newUser = new UserInfo();
    UserLoginInfo userLoginInfo = new UserLoginInfo();
    Receipt receipt = new Receipt();

    /**
     * Start method contains all javafx fields
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

//startup scene with logo
        ImageView titleImg = new ImageView("projectImages/title.jpg");
        titleImg.setFitHeight(b);
        titleImg.setFitWidth(a);
        titleImg.autosize();

        Button btn2 = new Button();
        btn2.setTranslateY(235);
        btn2.setMaxSize(150, 50);
        btn2.setAlignment(Pos.CENTER);

        btn2.setText("Book NOW");
        btn2.setOnAction(e -> primaryStage.setScene(scene1));

        StackPane root = new StackPane();
        root.getChildren().addAll(titleImg, btn2);

        scene = new Scene(root, a, b);

//first scene - enter trip info (numchildren/adults, numdays)
        Label title1 = new Label("Choose Trip Duration");
        title1.setStyle("-fx-font-size: 20;");
        title1.setAlignment(Pos.CENTER);

        GridPane gridPaneGuest = new GridPane();
        gridPaneGuest.setPadding(new Insets(15));
        gridPaneGuest.setHgap(20);
        gridPaneGuest.setVgap(5);
        gridPaneGuest.setAlignment(Pos.CENTER);

        VBox guestRoot = new VBox();
        guestRoot.setAlignment(Pos.CENTER);
        guestRoot.setStyle("-x-font-size: 60;");

        Label lblChildren = new Label("Choose the number of Children");
        ComboBox<String> cmbChildren = new ComboBox<>();
        String[] numOfChildren = {"0", "1", "2", "3", "4", "5", "6", "7"};
        cmbChildren.setItems(FXCollections.observableArrayList(numOfChildren));
        cmbChildren.getSelectionModel().select(0);
        gridPaneGuest.add(lblChildren, 0, 0);
        gridPaneGuest.add(cmbChildren, 0, 2);

        Label lblAdult = new Label("Choose the number of Adults");
        ComboBox<String> cmbAdult = new ComboBox<>();
        String[] numOfAdults = {"1", "2", "3", "4", "5", "6", "7"};
        cmbAdult.setItems(FXCollections.observableArrayList(numOfAdults));
        cmbAdult.getSelectionModel().select(0);
        gridPaneGuest.add(lblAdult, 2, 0);
        gridPaneGuest.add(cmbAdult, 2, 2);

        //code to create calender, src: https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.htm
        VBox vbox = new VBox(20);

        checkInDatePicker = new DatePicker();
        checkOutDatePicker = new DatePicker();
        checkInDatePicker.setValue(LocalDate.now());

        final Callback<DatePicker, DateCell> dayCellFactory
                = (final DatePicker datePicker) -> new DateCell() {
            //restrictItem(checkInDatePicker, true);

            public void restrictItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isEqual(
                        checkInDatePicker.getValue())) {
                    setDisable(true);
                    setStyle("-fx-background-color: ##DCDCDC;");
                }

            }
        };

        final Callback<DatePicker, DateCell> dayCellFactory2
                = (final DatePicker datePicker) -> new DateCell() {

            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(
                        checkInDatePicker.getValue().plusDays(1))) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
                long p = ChronoUnit.DAYS.between(
                        checkInDatePicker.getValue(), item
                );
                setTooltip(new Tooltip(
                        "You're about to stay for " + p + " days")
                );
            }
        };

        checkOutDatePicker.setDayCellFactory(dayCellFactory2);
        checkInDatePicker.setDayCellFactory(dayCellFactory);
        checkOutDatePicker.setValue(checkInDatePicker.getValue().plusDays(1));

        GridPane calender = new GridPane();
        calender.setHgap(10);
        calender.setVgap(20);
        calender.setAlignment(Pos.CENTER);

        Label checkInlabel = new Label("Check-In Date:");
        calender.add(checkInlabel, 0, 0);
        GridPane.setHalignment(checkInlabel, HPos.LEFT);
        calender.add(checkInDatePicker, 0, 1);

        Label checkOutlabel = new Label("Check-Out Date:");
        calender.add(checkOutlabel, 0, 2);
        GridPane.setHalignment(checkOutlabel, HPos.LEFT);
        calender.add(checkOutDatePicker, 0, 3);
        vbox.getChildren().add(calender);

        Button confirmDatesBtn = new Button("Confirm");
        confirmDatesBtn.setOnAction(e -> confirmDates(primaryStage, cmbChildren.getValue(), cmbAdult.getValue()));
        confirmDatesBtn.setTranslateX(15);
        confirmDatesBtn.setTranslateY(50);
        setbtnStyle(confirmDatesBtn);

        guestRoot.getChildren().addAll(title1, gridPaneGuest, vbox, confirmDatesBtn);

        scene1 = new Scene(guestRoot, 550, 550);

//second scene - selecting resort 
        Label title2 = new Label("Resort Selection ");
        title2.setAlignment(Pos.CENTER);
        title2.setStyle("-fx-font-size: 20;");

        VBox rootVbox = new VBox();
        rootVbox.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        ImageView resort1 = new ImageView("projectImages/africa1.jpg");
        resort1.setFitHeight(250);
        resort1.setFitWidth(150);
        resort1.setPickOnBounds(true);
        resort1.setOnMouseEntered(e -> onHover(resort1));
        resort1.setOnMouseExited(e -> offHover(resort1));
        Tooltip r1 = new Tooltip(resort.getResort(1).toString());
        Tooltip.install(resort1, r1);

        ImageView resort2 = new ImageView("projectImages/africa2.jpg");
        resort2.setFitHeight(250);
        resort2.setFitWidth(150);
        resort2.setPickOnBounds(true);
        resort2.setOnMouseEntered(e -> onHover(resort2));
        resort2.setOnMouseExited(e -> offHover(resort2));
        Tooltip r2 = new Tooltip(resort.getResort(2).toString());
        Tooltip.install(resort2, r2);

        ImageView resort3 = new ImageView("projectImages/africa3.jpg");
        resort3.setFitHeight(250);
        resort3.setFitWidth(150);
        resort3.setPickOnBounds(true);
        resort3.setOnMouseEntered(e -> onHover(resort3));
        resort3.setOnMouseExited(e -> offHover(resort3));
        Tooltip r3 = new Tooltip(resort.getResort(3).toString());
        Tooltip.install(resort3, r3);

        ToggleGroup group1 = new ToggleGroup();
        gridPane.add((resort1), 0, 0);
        box1 = new RadioButton("Safari");
        box1.setToggleGroup(group1);

        gridPane.add(box1, 0, 3);
        GridPane.setHalignment(box1, HPos.CENTER);

        gridPane.add((resort2), 1, 0);
        box2 = new RadioButton("Beach");
        box2.setToggleGroup(group1);

        gridPane.add(box2, 1, 3);
        GridPane.setHalignment(box2, HPos.CENTER);

        gridPane.add((resort3), 2, 0);
        box3 = new RadioButton("Mountain");
        box3.setToggleGroup(group1);

        gridPane.add(box3, 2, 3);
        GridPane.setHalignment(box3, HPos.CENTER);

        Button btnconfirmResort = new Button("Confirm");
        btnconfirmResort.setMaxWidth(100);
        btnconfirmResort.setOnAction(e -> confirmResort(primaryStage));
        setbtnStyle(btnconfirmResort);

        Button btnHelp1 = new Button("?");
        btnHelp1.setMaxWidth(50);
        btnHelp1.setOnAction(
                event -> info());
        setbtnStyle(btnHelp1);

        //lets the user edit their initial info
        Button backToDates = new Button("Back");
        backToDates.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(scene1);
        });
        setbtnStyle(backToDates);

        gridPane.add(btnconfirmResort, 1, 7);
        gridPane.add(backToDates, 0, 7);
        gridPane.add(btnHelp1, 2, 7);
        gridPane.setHalignment(btnHelp1, HPos.RIGHT);
        GridPane.setHalignment(btnconfirmResort, HPos.CENTER);

        rootVbox.getChildren().addAll(title2, gridPane);

        scene2 = new Scene(rootVbox, 700, 700);

//third - select rooms  
        Label title3 = new Label("Type Of Rooms ");
        title3.setAlignment(Pos.CENTER);
        title3.setStyle("-fx-font-size: 20;");

        VBox rootVBox = new VBox();
        rootVBox.setAlignment(Pos.CENTER);

        GridPane gridPane2 = new GridPane();
        gridPane2.setHgap(10);
        gridPane2.setVgap(10);
        gridPane2.setAlignment(Pos.CENTER);

        ImageView card1 = new ImageView("projectImages/1.jpg");
        card1.setFitHeight(150);
        card1.setFitWidth(150);
        card1.setPickOnBounds(true);
        Tooltip t = new Tooltip(resort.getRoom(1).toString());
        Tooltip.install(card1, t);

        ImageView card2 = new ImageView("projectImages/2.jpg");
        card2.setFitHeight(150);
        card2.setFitWidth(150);
        card2.setPickOnBounds(true);
        Tooltip t2 = new Tooltip(resort.getRoom(2).toString());
        Tooltip.install(card2, t2);

        ImageView card3 = new ImageView("projectImages/3.jpg");
        card3.setFitHeight(150);
        card3.setFitWidth(150);
        card3.setPickOnBounds(true);
        Tooltip t3 = new Tooltip(resort.getRoom(3).toString());
        Tooltip.install(card3, t3);

        ImageView card4 = new ImageView("projectImages/4.jpg");
        card4.setFitHeight(150);
        card4.setFitWidth(150);
        card4.setPickOnBounds(true);
        Tooltip t4 = new Tooltip(resort.getRoom(4).toString());
        Tooltip.install(card4, t4);

        ImageView card5 = new ImageView("projectImages/5.jpg");
        card5.setFitHeight(150);
        card5.setFitWidth(150);
        card5.setPickOnBounds(true);
        Tooltip t5 = new Tooltip(resort.getRoom(5).toString());
        Tooltip.install(card5, t5);

        ImageView card6 = new ImageView("projectImages/6.jpg");
        card6.setFitHeight(150);
        card6.setFitWidth(150);
        card6.setPickOnBounds(true);
        Tooltip t6 = new Tooltip(resort.getRoom(6).toString());
        Tooltip.install(card6, t6);

        ImageView card7 = new ImageView("projectImages/7.jpg");
        card7.setFitHeight(150);
        card7.setFitWidth(150);
        card7.setPickOnBounds(true);
        Tooltip t7 = new Tooltip(resort.getRoom(7).toString());
        Tooltip.install(card7, t7);

        ImageView card8 = new ImageView("projectImages/8.jpg");
        card8.setFitHeight(150);
        card8.setFitWidth(150);
        card8.setPickOnBounds(true);
        Tooltip t8 = new Tooltip(resort.getRoom(8).toString());
        Tooltip.install(card8, t8);

        ImageView card9 = new ImageView("projectImages/9.jpg");
        card9.setFitHeight(150);
        card9.setFitWidth(150);
        card9.setPickOnBounds(true);
        Tooltip t9 = new Tooltip(resort.getRoom(9).toString());
        Tooltip.install(card9, t9);

        gridPane2.add(card1, 0, 0);
        bedBox1 = new CheckBox(resort.getRoom(1).getName());
        gridPane2.add(bedBox1, 0, 1);
        GridPane.setHalignment(bedBox1, HPos.CENTER);
        style(bedBox1);

        gridPane2.add(card2, 0, 2);
        bedBox2 = new CheckBox(resort.getRoom(2).getName());
        gridPane2.add(bedBox2, 0, 3);
        GridPane.setHalignment(bedBox2, HPos.CENTER);
        style(bedBox2);

        gridPane2.add(card3, 0, 4);
        bedBox3 = new CheckBox(resort.getRoom(3).getName());
        gridPane2.add(bedBox3, 0, 6);
        GridPane.setHalignment(bedBox3, HPos.CENTER);
        style(bedBox3);

        gridPane2.add(card4, 1, 0);
        bedBox4 = new CheckBox(resort.getRoom(4).getName());
        gridPane2.add(bedBox4, 1, 1);
        GridPane.setHalignment(bedBox4, HPos.CENTER);
        style(bedBox4);

        gridPane2.add(card5, 1, 2);
        bedBox5 = new CheckBox(resort.getRoom(5).getName());
        gridPane2.add(bedBox5, 1, 3);
        GridPane.setHalignment(bedBox5, HPos.CENTER);
        style(bedBox5);

        gridPane2.add(card6, 1, 4);
        bedBox6 = new CheckBox(resort.getRoom(6).getName());
        gridPane2.add(bedBox6, 1, 6);
        GridPane.setHalignment(bedBox6, HPos.CENTER);
        style(bedBox6);

        gridPane2.add(card7, 2, 0);
        bedBox7 = new CheckBox(resort.getRoom(7).getName());
        gridPane2.add(bedBox7, 2, 1);
        GridPane.setHalignment(bedBox7, HPos.CENTER);
        style(bedBox7);

        gridPane2.add(card8, 2, 2);
        bedBox8 = new CheckBox(resort.getRoom(8).getName());
        gridPane2.add(bedBox8, 2, 3);
        GridPane.setHalignment(bedBox8, HPos.CENTER);
        style(bedBox8);

        gridPane2.add(card9, 2, 4);
        bedBox9 = new CheckBox(resort.getRoom(9).getName());
        gridPane2.add(bedBox9, 2, 6);
        GridPane.setHalignment(bedBox9, HPos.CENTER);
        style(bedBox9);

        Button btnconfirmRoom = new Button("Confirm");
        btnconfirmRoom.setMaxWidth(100);
        setbtnStyle(btnconfirmRoom);
        btnconfirmRoom.setOnAction(e -> confirmRoom(primaryStage)
        );

        gridPane2.add(btnconfirmRoom, 1, 8);
        GridPane.setHalignment(btnconfirmRoom, HPos.CENTER);

        Button btnHelp = new Button("?");
        btnHelp.setMaxWidth(50);
        btnHelp.setOnAction(
                event -> info());
        setbtnStyle(btnHelp);

        //lets the user edit their resort choice
        Button backToResort = new Button("Back");
        backToResort.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(scene2);
        });
        setbtnStyle(backToResort);

        gridPane2.add(btnHelp, 2, 8);
        gridPane2.add(backToResort, 0, 8);
        GridPane.setHalignment(btnHelp, HPos.RIGHT);

        rootVBox.getChildren().addAll(title3, gridPane2);

        scene3 = new Scene(rootVBox, 800, 700);

        //adding rooms to arraylist to be used later (must be done outside of methods to avoid error)
        tempRoom.add(bedBox1);
        tempRoom.add(bedBox2);
        tempRoom.add(bedBox3);
        tempRoom.add(bedBox4);
        tempRoom.add(bedBox5);
        tempRoom.add(bedBox6);
        tempRoom.add(bedBox7);
        tempRoom.add(bedBox8);
        tempRoom.add(bedBox9);

//fourth - select activities (display activities in array that match location)
        Label title4 = new Label("Choosing Activities ");
        title4.setAlignment(Pos.CENTER);
        title4.setStyle("-fx-font-size: 20;");

        VBox rootVertical = new VBox(10);
        rootVertical.setAlignment(Pos.CENTER);
        VBox vertical = new VBox(20);
        vertical.setAlignment(Pos.CENTER);
        vertical.setMinSize(200, 200);

        Label lblTitle = new Label("Please select as many additional activities our resort offers (optional - you do not need to pick any)");
        vertical.getChildren().add(listview);

        Button confirmActivitiesBtn = new Button("Select Activity");
        confirmActivitiesBtn.setOnAction(e -> confirmActivities(primaryStage, listview));
        setbtnStyle(confirmActivitiesBtn);

        //help for the user
        Button btnHelp2 = new Button("?");
        btnHelp2.setMaxWidth(50);
        btnHelp2.setOnAction(
                event -> info2());
        setbtnStyle(btnHelp2);

        //lets the user edit their room choices
        Button backToRooms = new Button("Back");
        backToRooms.setOnAction((ActionEvent event) -> {
            bedrooms.clear();
            primaryStage.setScene(scene3);
        });
        setbtnStyle(backToRooms);

        //setting the buttons in to the gridpane
        GridPane activityPane = new GridPane();
        activityPane.add(backToRooms, 0, 0);
        activityPane.add(confirmActivitiesBtn, 1, 0);
        activityPane.add(btnHelp2, 2, 0);
        activityPane.setHgap(70);
        activityPane.setAlignment(Pos.CENTER);

        rootVertical.getChildren().addAll(title4, lblTitle, vertical, activityPane);

        scene4 = new Scene(rootVertical, 600, 700);

//fifth - form fields for user input 
        //validates if the entered name is correct 
        Label title5 = new Label("Customer Information ");
        title5.setAlignment(Pos.CENTER);
        title5.setStyle("-fx-font-size: 20;");

        //declaration of the labels
        Label name = new Label("First Name :");
        Label error_label = new Label("Valid");
        //error_label.setFont(font);

        Label last_name_labe2 = new Label("Last Name :");
        Label error_labe2 = new Label("Valid");

        Label emailAddress = new Label("Email :");
        Label error_labe3 = new Label("Valid");

        Label phoneNum = new Label("Phone :");
        Label error_labe4 = new Label("Valid");

        Label address = new Label("Address :");
        Label error_labe5 = new Label("Valid");

        Label city = new Label("City :");
        Label error_labe6 = new Label("Valid");

        Label province = new Label("Province :");
        Label error_labe7 = new Label("Valid");

        Label country = new Label("Country :");
        Label error_labe8 = new Label("Valid");

        Label zipCode = new Label("PostalCode :");
        Label error_labe9 = new Label("Valid");

        Label cardHolder = new Label("CardHolder First Name :");
        Label error_labe10 = new Label("Valid");

        Label cardHolder2 = new Label("CardHolder Last Name :");
        Label error_labe11 = new Label("Valid");
        //error_labe10.setFont(font);

        Label cardNum = new Label("Credit Card Number :");
        Label error_labe12 = new Label("Valid");

        GridPane rootValidation = new GridPane();
        rootValidation.setHgap(10);
        rootValidation.setPadding(new Insets(5, 5, 5, 5));
        rootValidation.setMaxWidth(600);
        rootValidation.setMaxHeight(200);

        //validating and setting values for the textfields
        firstIn = new TextField();
        firstIn.setPromptText("First name");
        firstIn.setMinWidth(200);
        firstIn.setOnKeyReleased((KeyEvent event) -> {
            if (isValidName(firstIn.getText())) {
                error_label.setStyle("-fx-text-fill:green");
                error_label.setText("Valid");
            } else {
                error_label.setText("Invalid first Name");
                error_label.setStyle("-fx-text-fill:red");
            }
        });

        lastIn = new TextField();
        lastIn.setPromptText("Last Name");
        lastIn.setMinWidth(200);
        lastIn.setOnKeyReleased((KeyEvent event) -> {
            if (isValidName(lastIn.getText())) {
                error_labe2.setStyle("-fx-text-fill:green");
                error_labe2.setText("Valid");
            } else {
                error_labe2.setText("Invalid Last Name");
                error_labe2.setStyle("-fx-text-fill:red");
            }
        });

        emailIn = new TextField();
        emailIn.setPromptText("ABC@ABC.COM");
        emailIn.setMinWidth(200);
        emailIn.setOnKeyReleased((KeyEvent event) -> {
            if (isValidEmail(emailIn.getText())) {
                error_labe3.setStyle("-fx-text-fill:green");
                error_labe3.setText("Valid");
            } else {
                error_labe3.setText("Invalid Email");
                error_labe3.setStyle("-fx-text-fill:red");
            }
        });

        phoneIn = new TextField();
        phoneIn.setPromptText("123-123-1234");
        phoneIn.setMinWidth(200);
        phoneIn.setOnKeyReleased((KeyEvent event) -> {
            if (isValidPhone(phoneIn.getText())) {
                error_labe4.setStyle("-fx-text-fill:green");
                error_labe4.setText("Valid");
            } else {
                error_labe4.setText("Invalid Phone");
                error_labe4.setStyle("-fx-text-fill:red");
            }
        });

        addressIn = new TextField();
        addressIn.setPromptText("Street Name");
        addressIn.setMinWidth(200);
        addressIn.setOnKeyReleased((KeyEvent event) -> {
            if (isValidName(addressIn.getText())) {
                error_labe5.setStyle("-fx-text-fill:green");
                error_labe5.setText("Valid");
            } else {
                error_labe5.setText("Invalid Address");
                error_labe5.setStyle("-fx-text-fill:red");
            }
        });

        cityIn = new TextField();
        cityIn.setPromptText("City Name");
        cityIn.setMinWidth(200);
        cityIn.setOnKeyReleased((KeyEvent event) -> {
            if (isValidName(cityIn.getText())) {
                error_labe6.setStyle("-fx-text-fill:green");
                error_labe6.setText("Valid");
            } else {
                error_labe6.setText("Invalid City");
                error_labe6.setStyle("-fx-text-fill:red");
            }
        });

        provinceIn = new TextField();
        provinceIn.setPromptText("Province Name");
        provinceIn.setMinWidth(200);
        provinceIn.setOnKeyReleased((KeyEvent event) -> {
            if (isValidName(provinceIn.getText())) {
                error_labe7.setStyle("-fx-text-fill:green");
                error_labe7.setText("Valid");
            } else {
                error_labe7.setText("Invalid Province");
                error_labe7.setStyle("-fx-text-fill:red");
            }
        });

        countryIn = new TextField();
        countryIn.setPromptText("Country Name");
        countryIn.setMinWidth(200);
        countryIn.setOnKeyReleased((KeyEvent event) -> {
            if (isValidName(countryIn.getText())) {
                error_labe8.setStyle("-fx-text-fill:green");
                error_labe8.setText("Valid");
            } else {
                error_labe8.setText("Invalid Country");
                error_labe8.setStyle("-fx-text-fill:red");
            }
        });

        zipIn = new TextField();
        zipIn.setPromptText("X4X 5T5");
        zipIn.setMinWidth(200);
        zipIn.setOnKeyReleased((KeyEvent event) -> {
            if (isValidPostal(zipIn.getText())) {
                error_labe9.setStyle("-fx-text-fill:green");
                error_labe9.setText("Valid");
            } else {
                error_labe9.setText("Invalid Postal");
                error_labe9.setStyle("-fx-text-fill:red");
            }
        });

        cardHolderFirst = new TextField();
        cardHolderFirst.setPromptText("Card First Name");
        cardHolderFirst.setMinWidth(200);
        cardHolderFirst.setOnKeyReleased((KeyEvent event) -> {
            if (isValidName(cardHolderFirst.getText())) {
                error_labe10.setStyle("-fx-text-fill:green");
                error_labe10.setText("Valid");
            } else {
                error_labe10.setText("Invalid Card First Name");
                error_labe10.setStyle("-fx-text-fill:red");
            }
        });

        cardHolderLast = new TextField();
        cardHolderLast.setPromptText("Card Last Name");
        cardHolderLast.setMinWidth(200);
        cardHolderLast.setOnKeyReleased((KeyEvent event) -> {
            if (isValidName(cardHolderLast.getText())) {
                error_labe11.setStyle("-fx-text-fill:green");
                error_labe11.setText("Valid");
            } else {
                error_labe11.setText("Invalid Card Last Name");
                error_labe11.setStyle("-fx-text-fill:red");
            }
        });

        cardInfo = new TextField();
        cardInfo.setPromptText("1234567891234");
        cardInfo.setMinWidth(200);
        cardInfo.setOnKeyReleased((KeyEvent event) -> {
            if (isValidCardNum(cardInfo.getText())) {
                error_labe12.setStyle("-fx-text-fill:green");
                error_labe12.setText("Valid");
            } else {
                error_labe12.setText("Invalid Card Number");
                error_labe12.setStyle("-fx-text-fill:red");
            }
        });

        //adding the values to the grid pane
        rootValidation.add(name, 0, 0);
        rootValidation.add(firstIn, 1, 0);
        rootValidation.add(error_label, 2, 0);

        rootValidation.add(last_name_labe2, 0, 1);
        rootValidation.add(lastIn, 1, 1);
        rootValidation.add(error_labe2, 2, 1);

        rootValidation.add(emailAddress, 0, 2);
        rootValidation.add(emailIn, 1, 2);
        rootValidation.add(error_labe3, 2, 2);

        rootValidation.add(phoneNum, 0, 3);
        rootValidation.add(phoneIn, 1, 3);
        rootValidation.add(error_labe4, 2, 3);

        rootValidation.add(address, 0, 4);
        rootValidation.add(addressIn, 1, 4);
        rootValidation.add(error_labe5, 2, 4);

        rootValidation.add(city, 0, 5);
        rootValidation.add(cityIn, 1, 5);
        rootValidation.add(error_labe6, 2, 5);

        rootValidation.add(province, 0, 6);
        rootValidation.add(provinceIn, 1, 6);
        rootValidation.add(error_labe7, 2, 6);

        rootValidation.add(country, 0, 7);
        rootValidation.add(countryIn, 1, 7);
        rootValidation.add(error_labe8, 2, 7);

        rootValidation.add(zipCode, 0, 8);
        rootValidation.add(zipIn, 1, 8);
        rootValidation.add(error_labe9, 2, 8);

        rootValidation.add(cardHolder, 0, 9);
        rootValidation.add(cardHolderFirst, 1, 9);
        rootValidation.add(error_labe10, 2, 9);

        rootValidation.add(cardHolder2, 0, 10);
        rootValidation.add(cardHolderLast, 1, 10);
        rootValidation.add(error_labe11, 2, 10);

        rootValidation.add(cardNum, 0, 11);
        rootValidation.add(cardInfo, 1, 11);
        rootValidation.add(error_labe12, 2, 11);

        rootValidation.setAlignment(Pos.CENTER);

        //creating the buttons of confirmation on the page
        Button btnConfirmInfo = new Button("Submit");
        btnConfirmInfo.setOnAction(e -> confirmInfo(primaryStage));
        setbtnStyle(btnConfirmInfo);

        GridPane signIn = new GridPane();
        signIn.setHgap(10);
        signIn.setPadding(new Insets(5, 5, 5, 5));
        signIn.setAlignment(Pos.CENTER);
        Label usernameLbl = new Label("Username: ");
        signIn.add(usernameLbl, 0, 0);
        TextField username = new TextField();
        signIn.add(username, 0, 1);
        Label passwordLbl = new Label("Password");
        signIn.add(passwordLbl, 1, 0);
        TextField password = new TextField();
        signIn.add(password, 1, 1);

        //lets the user edit their activity choices
        Button backToActivities = new Button("Back");
        backToActivities.setOnAction((ActionEvent event) -> {
            activities.clear();
            primaryStage.setScene(scene4);
        });
        setbtnStyle(backToActivities);

        //setting the buttons on scene 5
        HBox hBoxButtons = new HBox();
        hBoxButtons.getChildren().addAll(backToActivities, btnConfirmInfo);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setPadding(new Insets(10));

        //setting the vbox for the UserInfromation page
        VBox userInformation = new VBox();
        userInformation.setAlignment(Pos.CENTER);

        //creating  the login button
        Button loginBtn = new Button("Login");
        loginBtn.setAlignment(Pos.BOTTOM_LEFT);
        loginBtn.setOnAction(e -> modifyButton(username.getText(), password.getText(), userInformation));
        setbtnStyle(loginBtn);

        userInformation.getChildren().addAll(rootValidation, hBoxButtons, signIn, loginBtn);

        scene5 = new Scene(userInformation, 600, 500);

//sixth - show summary of trip details and user information, give users a chance to make changes 
        //lets the user edit their initial info
        Label title6 = new Label("Detailed Summary ");
        title6.setStyle("-fx-font-size: 20;");
        title6.setAlignment(Pos.TOP_CENTER);

        Button editDates = new Button("Change Guests or Dates");
        editDates.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(scene1);
        });
        setbtnStyle(editDates);

        //lets the user edit their resort - GOOD
        Button editResort = new Button("Change Resort");
        editResort.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(scene2);
        });
        setbtnStyle(editResort);

        //let user edit rooms - GOOD
        Button editRoom = new Button("Change Rooms");
        editRoom.setOnAction((ActionEvent event) -> {
            bedrooms.clear();
            primaryStage.setScene(scene3);
            
        });
        setbtnStyle(editRoom);

        //lets user edit activities 
        Button editActivities = new Button("Change Activities");
        editActivities.setOnAction((ActionEvent event) -> {
            activities.clear();
            primaryStage.setScene(scene4);
            
        });
        setbtnStyle(editActivities);

        //lets user edit their personal info
        Button editUserInfo = new Button("Change Personal Information");
        editUserInfo.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(scene5);
        });
        setbtnStyle(editUserInfo);

        tripDetails = new Label();
        tripDetails.setPrefSize(400, 600);
        tripDetails.setStyle(
                "-fx-border-color: black; "
                + "-fx-font-size: 15;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;"
        );
        tripDetails.setAlignment(Pos.CENTER);

        userDetails = new Label();
        userDetails.setPrefSize(400, 600);
        userDetails.setStyle(
                "-fx-border-color: black; "
                + "-fx-font-size: 15;"
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 1;"
        );
        userDetails.setAlignment(Pos.CENTER);

        VBox userSummary = new VBox();
        userSummary.setPadding(new Insets(10));
        Label summaryTemp = new Label("Summary of trip and user info");
        summaryTemp.setAlignment(Pos.CENTER);
        Button finalConfirm = new Button("CONFIRM");
        finalConfirm.setOnAction(e -> confirmTrip(primaryStage));

        setbtnStyle(finalConfirm);
        finalConfirm.setAlignment(Pos.CENTER);
        userSummary.getChildren().addAll(summaryTemp, finalConfirm);
        userSummary.setAlignment(Pos.CENTER);

        HBox buttonsHBox = new HBox();
        buttonsHBox.getChildren().addAll(editDates, editResort, editRoom, editActivities, editUserInfo);
        buttonsHBox.setAlignment(Pos.CENTER);
        HBox dataDisplay = new HBox();
        dataDisplay.getChildren().addAll(tripDetails, userDetails);
        dataDisplay.setAlignment(Pos.CENTER);
        VBox centerBorderPane = new VBox(buttonsHBox, dataDisplay);

        BorderPane summaryBorder = new BorderPane();
        summaryBorder.setTop(title6);
        summaryBorder.setCenter(centerBorderPane);
        summaryBorder.setBottom(userSummary);
        summaryBorder.setPadding(new Insets(10, 10, 10, 10));

        scene6 = new Scene(summaryBorder, 1000, 700);

//seventh - final window now says thank you for booking with us, can exit or save receipt file
        ImageView endImg = new ImageView("projectImages/Thankyou.jpg");
        endImg.setFitHeight(600);
        endImg.setFitWidth(900);
        endImg.autosize();

        //terminating the program
        Button btnFinish = new Button("Finish");
        btnFinish.setOnAction(e -> System.exit(0));
        setbtnStyle(btnFinish);

        //save the receipt
        Button btnSaveFile = new Button("Save Receipt");
        btnSaveFile.setOnAction(e -> receipt.writeFile(primaryStage, newTrip, newUser));
        setbtnStyle(btnSaveFile);

        // adding the end button to gridpane
        GridPane endPane = new GridPane();
        endPane.add(btnSaveFile, 0, 3);
        endPane.add(btnFinish, 2, 3);
        endPane.setHgap(80);
        endPane.setPadding(new Insets(40));
        endPane.setAlignment(Pos.BOTTOM_CENTER);

        //adding the buttons on the img
        StackPane rootEnd = new StackPane();
        rootEnd.getChildren().addAll(endImg, endPane);

        scene7 = new Scene(rootEnd, 900, 600);

        primaryStage.setTitle("easyBook - Book Your Resort Now");
        primaryStage.setScene(scene);
        primaryStage.show();

    }// end of the start method

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        Locale.setDefault(Locale.US);
    }

    /**
     * Methods to create info buttons
     */
    private void info() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("HELP");
        info.setHeaderText("Hover over the image to find the information");
        Optional<ButtonType> result = info.showAndWait();
    }

    private void info2() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("HELP");
        info.setHeaderText("Activity Selection");
        info.setContentText("Press Ctrl on to select multiple");
        Optional<ButtonType> result = info.showAndWait();
    }

    /**
     * Methods that allow user to hover over resort images, adjusting size for
     * viewing
     *
     * @param card
     */
    private void onHover(ImageView card) {
        card.setFitHeight(350);
        card.setFitWidth(250);
    }

    private void offHover(ImageView card) {
        card.setFitHeight(250);
        card.setFitWidth(150);
    }

    /**
     * Method to set the styling for buttons for consistent look
     *
     * @param btn
     */
    private void setbtnStyle(Button btn) {
        btn.setStyle(
                "-fx-border-color: lightblue; "
                + "-fx-font-size: 15;"
                + "-fx-border-insets: 2; "
                + "-fx-border-radius: 5;"
                + "-fx-border-width: 2;"
        );
    }

    /**
     * Method to set style on checkbox fields
     *
     * @param box1
     */
    private void style(CheckBox box1) {
        box1.setStyle(
                "-fx-border-color: lightblue; "
                + "-fx-font-size: 15;"
                + "-fx-border-insets: -5; "
                + "-fx-border-radius: 5;"
                + "-fx-border-style: dotted;"
                + "-fx-border-width: 2;"
        );
    }

    /**
     * Method to confirm how many children and adults are attending the trip and
     * duration of the trip in days; these numbers will be saved to variables
     * and passed to the Trip constructor
     *
     * @param primaryStage
     * @param cmbChildren
     * @param cmbAdult
     */
    private void confirmDates(Stage primaryStage, String cmbChildren, String cmbAdult) {
        numChildren = Integer.parseInt(cmbChildren);
        numAdults = Integer.parseInt(cmbAdult);

        //calculate number of days between dates picked
        //src https://www.baeldung.com/java-date-difference
        Period period = Period.between(checkInDatePicker.getValue(), checkOutDatePicker.getValue());
        numDays = period.getDays();

        primaryStage.setScene(scene2);
    }

    /**
     * Method to ensure a resort has been selected; if resort is selected, that
     * resort will be saved to a Resort object variable and later added to the
     * Trip constructor; if valid selection is made, next scene is shown to pick
     * rooms
     *
     * @param primaryStage
     * @return
     */
    public Resort confirmResort(Stage primaryStage) {

        if (box1.isSelected()) {
            userResortChoice = 1;
            chosenResort = resort.getResort(userResortChoice);
            primaryStage.setScene(scene3);

        } else if (box2.isSelected()) {
            userResortChoice = 2;
            chosenResort = resort.getResort(userResortChoice);
            primaryStage.setScene(scene3);

        } else if (box3.isSelected()) {
            userResortChoice = 3;
            chosenResort = resort.getResort(userResortChoice);
            primaryStage.setScene(scene3);

        } else {
            Alert resortwarning = new Alert(AlertType.CONFIRMATION);
            resortwarning.setTitle("Error");
            resortwarning.setHeaderText("No Resort is selected!");
            resortwarning.setContentText("Please select a resort to continue");
            resortwarning.showAndWait();
        }
        bedrooms.clear();
        return chosenResort;
        
    }

    /**
     * Method to ensure room/s have been selected, and that enough beds are
     * selected to fit number of guests chosen; if valid selection is made,
     * room/s are added to an arraylist called bedrooms which will be added to
     * the Trip constructor, and next scene is shown to pick activities
     *
     * @param primaryStage
     */
    public void confirmRoom(Stage primaryStage) {
        int canSleep = 0;

        for (CheckBox box : tempRoom) {
            if (box.isSelected()) {
                userRoomChoice = (tempRoom.indexOf(box) + 1);
                roomChoice = resort.getRoom(userRoomChoice);
                bedrooms.add(roomChoice);
                canSleep += roomChoice.getCanSleep();
            }
        }
        if (canSleep >= (numAdults + numChildren)) {
            primaryStage.setScene(scene4);
            displayActivities(listview);
        } else {
            Alert roomError = new Alert(AlertType.ERROR);
            roomError.setTitle("Error");
            roomError.setHeaderText("Not enough beds for guests!");
            roomError.setContentText("Please select enough beds for " + (numAdults + numChildren) + " person(s)");
            bedrooms.clear();
            roomError.showAndWait();
        }
    }

    /**
     * Method to create the listview of activities to select from; arraylist is
     * pulled in from the ResortCreator getActivities method, which creates an
     * arraylist of activities based on which resort is selected; this arraylist
     * is converted to an array and then passed in to the listview
     *
     * @param listview
     * @return
     */
    public ListView displayActivities(ListView listview) {
        activitiesTempList.clear();
        activities.clear();
        activitiesTempList = (resort.getActivities(chosenResort));
        //convert arraylist to array
        activityArray = activitiesTempList.toArray(new ResortActivity[activitiesTempList.size()]);

        //create the listview to be passed
        ObservableList activitiesTest = FXCollections.observableArrayList(activityArray);
        listview.setItems(activitiesTest);
        listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listview.setTooltip(new Tooltip("Select Activities"));

        return listview;
    }

    /**
     * Method to confirm if any activities are chosen; user does not need to
     * select activities, but if they do, they are added to an arraylist called
     * activities, which will be passed to the Trip constructor; when user
     * clicks confirm, next scene is shown to enter user information
     *
     * @param primaryStage
     * @param listview
     */
    private void confirmActivities(Stage primaryStage, ListView listview) {
        ObservableList<ResortActivity> listOfActivities;
        listOfActivities = listview.getSelectionModel().getSelectedItems();

        for (ResortActivity activity : listOfActivities) {
            activities.add(activity);
        }
        primaryStage.setScene(scene5);
    }

    /**
     * Method to add all user entered information to the UserInformation
     * constructor; if all input is valid, next scene is shown that displays
     * trip summary and user info summary which the user can still choose to
     * edit
     *
     * @param primaryStage
     */
    private void confirmInfo(Stage primaryStage) {
        //passing info to UserInfo 
        if (firstIn.getText().equals("") || lastIn.getText().equals("") || emailIn.getText().equals("") || phoneIn.getText().equals("")
                || addressIn.getText().equals("") || cityIn.getText().equals("") || provinceIn.getText().equals("")
                || countryIn.getText().equals("") || zipIn.getText().equals("") || cardInfo.getText().equals("")
                || cardHolderFirst.getText().equals("") || cardHolderLast.getText().equals("")) {
            Alert userInfoError = new Alert(AlertType.ERROR);
            userInfoError.setTitle("Error");
            userInfoError.setHeaderText("Incomplete Form!");
            userInfoError.setContentText("Please fill out complete form");
            userInfoError.showAndWait();
        } else {
            newUser.setFirstName(firstIn.getText());
            newUser.setLastName(lastIn.getText());
            newUser.setEmail(emailIn.getText());
            newUser.setPhone(phoneIn.getText());
            newUser.setAddress(addressIn.getText());
            newUser.setCity(cityIn.getText());
            newUser.setProvince(provinceIn.getText());
            newUser.setCountry(countryIn.getText());
            newUser.setZipcode(zipIn.getText());
            newUser.setCardNumber(cardInfo.getText());
            newUser.setCardHolderFN(cardHolderFirst.getText());
            newUser.setCardHolderLN(cardHolderLast.getText());

            createTrip(tripDetails, newUser, userDetails);
            primaryStage.setScene(scene6);

        }
    }

    /**
     * Optional button that pops up if the user successfully logs in; if the
     * user logs in, the form is autocompleted but this button allows them to
     * change a certain field, so that the next time they login, the modified
     * field will show up rather than the old field; eg, if they changed their
     * address and want the updated address to now be in the autocompleted form
     * in future logins
     *
     * @param username
     * @param password
     * @param userInformation
     * @return
     */
    public VBox modifyButton(String username, String password, VBox userInformation) {
        if ((username.equals("john") && password.equals("101"))
                || (username.equals("jane") && password.equals("102")) || (username.equals("mark") && password.equals("103"))) {
            Button editUser = new Button("Modify User Info");
            setbtnStyle(editUser);

            userInformation.getChildren().add(editUser);
            userLoginInfo.createRaf(newUser, username, password, firstIn, lastIn, emailIn, phoneIn, addressIn,
                    zipIn, provinceIn, cityIn, countryIn, cardHolderFirst, cardHolderLast, cardInfo, editUser);
        }
        return userInformation;
    }

    /**
     * Method for final scene; allows user to confirm if they are ready to
     * finalizetheir trip
     *
     * @param primaryStage
     */
    private void confirmTrip(Stage primaryStage) {
        Alert confirmMsg = new Alert(AlertType.CONFIRMATION);
        confirmMsg.setTitle("Confirmation");
        confirmMsg.setHeaderText("Selecting Confirm will finalize your trip");
        confirmMsg.setContentText("Do you wish to continue?");
        Optional<ButtonType> result = confirmMsg.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            primaryStage.setScene(scene7);
        } else {
            primaryStage.setScene(scene6);
        }
    }

    /**
     * Method to create the Trip constructor; all necessary variables are passed
     * into the constructor here
     *
     * @param tripDetails
     * @param user
     * @param userDetails
     */
    private void createTrip(Label tripDetails, UserInfo user, Label userDetails) {
        newTrip = new Trip(chosenResort, numChildren, numAdults, numDays, activities, bedrooms, cost);
        tripDetails.setText(newTrip.toString());
        userDetails.setText(user.toString());
    }

//form validation methods
    private void fixFirstName() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("fix First Name");
        alert.setContentText("Ooops, the name you have entered is not correct please Re-Enter");

        alert.showAndWait();
    }

    private void fixLastName() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("fix Last Name");
        alert.setContentText("Ooops, the name you have entered is not correct please Re-Enter");

        alert.showAndWait();
    }

    //validation of the regex expressions 
    public boolean isValidName(String s) {
        String regex = "[A-Za-z\\s]+";
        return s.matches(regex);//returns true if input and regex matches otherwise false;
    }

    public boolean isValidEmail(String s) {
        String regex = "^[a-zA-z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-z]{2,6}$";
        return s.matches(regex);//returns true if input and regex matches otherwise false;
    }

    public boolean isValidPhone(String s) {
        String regex = "\\d{3}-\\d{3}-\\d{4}";
        return s.matches(regex);//returns true if input and regex matches otherwise false;
    }

    public boolean isValidPostal(String s) {
        String regex = "^(?!.*[DFIOQU])[A-Za-z][0-9][A-Z] ?[0-9][A-Za-z][0-9]$";
        return s.matches(regex);//returns true if input and regex matches otherwise false;
    }

    public boolean isValidCardNum(String s) {
        String regex = "^\\d{13,16}$";
        return s.matches(regex);//returns true if input and regex matches otherwise false;
    }

}
