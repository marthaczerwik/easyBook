
package ResortBooking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Receipt class prints out a complete receipt for the user which contains their
 * trip summary as well as personal information, both of which are passed in
 * objects of type Trip and UserInfo
 * 
 * @author Martha Czerwik
 */
public class Receipt {
   public void writeFile(Stage primaryStage, Trip trip, UserInfo user) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Receipt");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));

        File file = chooser.showSaveDialog(primaryStage);

        if (file == null) {
            return; 
        }

        PrintWriter writer = null;
        try {
            FileWriter filewriter = new FileWriter(file, false);
            writer = new PrintWriter(filewriter);
            writer.println(trip.toString());
            writer.println(user.toString());
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        } catch (IOException io) {
            System.out.println(io.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }
}
