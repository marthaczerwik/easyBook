package ResortBooking;

/**
 * Resort class creates Resort objects based on name, location and price
 * @author Martha Czerwik
 */
public class Resort {

    private final String name;
    private final String location;
    private final double price;

    public Resort(String name, String location, double price) {
        this.name = name;
        this.location = location;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + ", $" + price;
    }

}
