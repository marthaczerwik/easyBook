package ResortBooking;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * ResortCreator creates set instances of resort objects, resort activities and
 * rooms, away from the main class that interacts with the user so the values
 * cannot be altered
 *
 * @author Martha Czerwik
 */
public class ResortCreator {

    ArrayList<ResortActivity> activityList = new ArrayList<>();
    ArrayList<Room> roomList = new ArrayList<>();

    /**
     * The user selects one of the resort options, and that value is passed
     * in to this method, which creates the resort type object to match the one
     * the user selected
     *
     * @param resortNumber
     * @return
     */
    public Resort getResort(int resortNumber) {
        Resort chosenResort = null;
        switch (resortNumber) {
            case 1:
                chosenResort = new Resort("Safari Resort", "Safari", 350.00);
                break;
            case 2:
                chosenResort = new Resort("Beach Resort", "Beach", 350.00);
                break;
            case 3:
                chosenResort = new Resort("Mountains Resort", "Mountains", 200.00);
                break;
        }
        return chosenResort;

    }

    /**
     * Arraylist  activityList is populated with all instances of activities; once 
     * the user selects a resort type, the resort type is passed in and any 
     * activities that do not have matching locations with the resort are removed
     * from the arraylist; this arraylist is then passed back to the main class,
     * which will later pass it to the Trip constructor
     *
     * @param resort
     * @return activityList
     */
    public ArrayList getActivities(Resort resort) {

        //create activities and add them to an arraylist
        ResortActivity chosenActivity1 = new ResortActivity("Snorkeling in the Sea", "Beach", 100.00);
        activityList.add(chosenActivity1);

        ResortActivity chosenActivity2 = new ResortActivity("Group Hiking up the Mountains", "Mountains", 200.00);
        activityList.add(chosenActivity2);

        ResortActivity chosenActivity3 = new ResortActivity("Safari Drive", "Safari", 300.00);
        activityList.add(chosenActivity3);

        ResortActivity chosenActivity4 = new ResortActivity("Dinner on the Sea", "Beach", 400.00);
        activityList.add(chosenActivity4);

        ResortActivity chosenActivity5 = new ResortActivity("Helicopter Tour Over the Mountains", "Mountains", 500.00);
        activityList.add(chosenActivity5);

        ResortActivity chosenActivity6 = new ResortActivity("Wildlife Course for Kids", "Safari", 600.00);
        activityList.add(chosenActivity6);

        ResortActivity chosenActivity7 = new ResortActivity("Elephant Riding", "Safari", 600.00);
        activityList.add(chosenActivity7);

        ResortActivity chosenActivity8 = new ResortActivity("Canoeing Down the River", "Safari", 600.00);
        activityList.add(chosenActivity8);

        ResortActivity chosenActivity9 = new ResortActivity("Hot Air Balloon Ride", "Mountains", 600.00);
        activityList.add(chosenActivity9);

        ResortActivity chosenActivity10 = new ResortActivity("Group Mountain Biking", "Mountains", 600.00);
        activityList.add(chosenActivity10);

        ResortActivity chosenActivity11 = new ResortActivity("Parasailing by the Coast", "Beach", 600.00);
        activityList.add(chosenActivity11);

        ResortActivity chosenActivity12 = new ResortActivity("Jetski Rentals", "Beach", 600.00);
        activityList.add(chosenActivity12);

        //loop through arraylist to see if location will match resort, if doesnt, delete from array so it isnt displayed
        //using Iterator to avoid ConcurrentModificationException --> ref: https://www.geeksforgeeks.org/remove-element-arraylist-java/
        Iterator itr = activityList.iterator();
        while (itr.hasNext()) {
            ResortActivity ra = (ResortActivity) itr.next();
            if (!ra.getLocation().equals(resort.getLocation())) {
                itr.remove();
            }
        }

        return activityList;
    }

    /**
     * Instances of rooms are created and displayed via checkboxes; user selects 
     * which rooms they would like 
     *
     * @return roomList
     * @param userRoomChoice
     */
    public Room getRoom(int userRoomChoice) {

        Room chosenRoom = null;
        switch (userRoomChoice) {
            case 1:
                chosenRoom = new Room("1 Single Bed", 1, 50.00);
                break;
            case 2:
                chosenRoom = new Room("1 Queen Bed", 2, 70.00);
                break;
            case 3:
                chosenRoom = new Room("1 King Bed", 2, 100.00);
                break;
            case 4:
                chosenRoom = new Room("2 Single Beds", 2, 100.00);
                break;
            case 5:
                chosenRoom = new Room("2 Queen Beds", 4, 140.00);
                break;
            case 6:
                chosenRoom = new Room("1 Queen 1 Single", 3, 50.00);
                break;
            case 7:
                chosenRoom = new Room("1 Queen 2 Singles", 4, 50.00);
                break;
            case 8:
                chosenRoom = new Room("1 King 1 single", 3, 50.00);
                break;
            case 9:
                chosenRoom = new Room("1 King 2 singles", 4, 50.00);
                break;
        }

        return chosenRoom;
    }

}
