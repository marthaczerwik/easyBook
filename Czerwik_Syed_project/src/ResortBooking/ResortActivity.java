package ResortBooking;

/**
 * Resort Activity creates activity objects based on name, location and price
 * Location of activities will be used to display activities that match each
 * resort, such as only displaying beach activities for resorts by the beach
 *
 * @author Martha Czerwik
 */
public class ResortActivity {

    private final String name;
    private final String location;
    private final double price;

    public ResortActivity(String name, String location, double price) {
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
        return "\n-" + name + ", $" + price;
    }

}
