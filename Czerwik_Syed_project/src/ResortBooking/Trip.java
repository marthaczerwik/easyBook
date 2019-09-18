package ResortBooking;

import java.util.ArrayList;

/**
 * Trip class holds information that the user enters about their trip, such as
 * number of children, adults and trip duration (number of days); A trip object
 * will be constructed based off of these selections, as well as which type of
 * resort (object of type Resort), which bedrooms (arraylist of objects of type
 * Room), and any additional activities (arraylist of objects of type ResortActivity) 
 * the user will select
 *
 * @author Martha Czerwik
 */
//change resort/room to abstract
public class Trip {

    private Resort resortType;
    private int numOfChildren;
    private int numOfAdults;
    private int numOfDays;
    private ArrayList<Room> rooms;
    private ArrayList<ResortActivity> activities;
    private double cost;

    public Trip() {
    }

    public Trip(Resort resortType, int numOfChildren, int numOfAdults, int numOfDays, ArrayList<ResortActivity> activities, ArrayList<Room> rooms, double cost) {
        this.resortType = resortType;
        this.numOfChildren = numOfChildren;
        this.numOfAdults = numOfAdults;
        this.numOfDays = numOfDays;
        this.rooms = rooms;
        this.activities = activities;
        this.cost = calculateCost();
    }

    public Resort getResortType() {
        return resortType;
    }

    public void setResortType(Resort resortType) {
        this.resortType = resortType;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public int getNumOfAdults() {
        return numOfAdults;
    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Trip Summary:\n\nResort: " + resortType + "\nGuests: "
                + numOfChildren + " Children, " + numOfAdults + " Adults"
                + "\nTrip Duration: " + numOfDays + " days " + "\nRooms: " + 
                rooms.toString().replace("[", "").replace("]", "").replace(",", "") + "\nAdditional "
                + "Activities: " + activities.toString().replace("[", "").replace("]", "").replace(",", "") 
                + "\n\nTotal: $" + cost + "\n";
    }

    private double calculateCost() {
        activities.forEach((activity) -> {
            cost += activity.getPrice();
        });
        
        rooms.forEach((room) -> {
            cost+= room.getPricePerNight() * numOfDays;
        });
        cost += resortType.getPrice();
        return cost;
    }
}
