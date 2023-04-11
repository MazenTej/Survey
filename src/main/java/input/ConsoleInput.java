package input;

import java.util.Scanner;

public class ConsoleInput implements InputDriver {

    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public int getIntegerInput(int i) {
        int userInput;

        while (true) {
            try {
                String choice = scanner.nextLine();
                choice.strip();
                userInput = Integer.parseInt(choice);
                if ((userInput < 1 || userInput > i)) {
                    throw new IllegalArgumentException();
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Must enter a number between 1 and " + Integer.toString(i));
            }
        }
        return userInput;
    }

    @Override
    public String readStringInput(int maxLines) {
        String userInput = "";
        int lineCounter = 0;
        while (lineCounter < maxLines) {
            userInput = userInput.concat(scanner.nextLine()).concat("\n");
            lineCounter++;
        }
        return userInput;
    }
}
