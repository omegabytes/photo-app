package GPCode;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by alex on 12/5/16.
 */
public class GFController {

    GFModel model = new GFModel();



    public void saveButtonPressed() {
        System.out.println("Save button pressed");
        //todo: handle save
    }

    public void searchButtonPressed(String text) throws IOException {
        System.out.println("Search button pressed");
        model.handleSearch(text);
    }

    public void loadButtonPressed() {
        System.out.println("Load button pressed");
        //todo: handle load
    }

    public void deleteButtonPressed() {
        System.out.println("Delete button pressed");
        //todo: handle delete
    }

    public void testButtonPressed() {
        System.out.println("Test button pressed");
        //todo: handle test
    }

    public void exitButtonPressed() {
        System.exit(0);
    }

}
