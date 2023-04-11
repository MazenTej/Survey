package output;

import java.io.Serializable;
import java.util.ArrayList;

public class ConsoleOutput implements OutputMenuDriver, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void displayNumberedOptions(ArrayList<String> menuOptions) {
        int numOption = 1;
        for (String option : menuOptions) {
            String optionDisplay = Integer.toString(numOption) + ". " + option;
            System.out.println(optionDisplay);
            numOption++;
        }
    }
}
