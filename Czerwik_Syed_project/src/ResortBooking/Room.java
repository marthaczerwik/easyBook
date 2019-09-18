package ResortBooking;


/**
 * Room class holds information about type of bedrooms the user can select for
 * their trip; room object will be constructed based on name of room, how many 
 * people it can sleep, and nightly price
 *
 * @author Martha Czerwik
 */
public class Room {

    private final String name;
    private final int canSleep;
    private final double pricePerNight;

    public Room(String name, int canSleep, double pricePerNight) {
        this.name = name;
        this.canSleep = canSleep;
        this.pricePerNight = pricePerNight;
    }

    public String getName() {
        return name;
    }

    public int getCanSleep() {
        return canSleep;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public String toString() {
        return "\n" + name + ":\n-Can Sleep " + canSleep + " Guests\n-Price Per Night: $" + pricePerNight + "\n";
    }

}
