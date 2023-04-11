import input.ConsoleInput;

import java.util.ArrayList;

public class MultipleChoice extends Question {

    private static final input.InputDriver inputDriver = new ConsoleInput();
    private static final long serialVersionUID = 1L;
    public ArrayList<String> choices;
    public int numResponses;


    public MultipleChoice(Question question, ArrayList<String> choices, int numResponses) {
        super(question.questionPrompt, question.questionNumber);
        this.choices = choices;
        this.numResponses = numResponses;
    }


    public ArrayList<String> validChoices() {
        ArrayList<String> validChoices = new ArrayList<>();
        int possibleChoices = 'a';
        for (int i = 0; i < choices.size(); i++) {
            String choice = Character.toString((char) (possibleChoices + i));
            validChoices.add(choice);
        }
        return validChoices;
    }

    @Override
    public void getCorrectResponse(Question question) {
        ArrayList<String> validChoices = validChoices();
        ArrayList<String> multipleResponses = new ArrayList<>();
        if (this.numResponses > 1) {
            for (int i = 0; i < this.numResponses; i++) {
                boolean checker = true;
                while (checker) {
                    System.out.print("Enter correct response " + (i + 1) + "(a:1/b:2/c:3...): ");
                    String response = inputDriver.readStringInput(1);
                    response = response.replace("\n", "");
                    if (validChoices.contains((response))) {
                        multipleResponses.add(response);
                        checker = false;
                    } else {
                        System.out.println("Please enter a valid response");
                    }
                }
            }
            if (!multipleResponses.isEmpty()) {
                ResponseCorrectAnswer validResponse = new ResponseCorrectAnswer(multipleResponses, true);
                validResponse.addMultipleResponsesToQuestion(question);
            }
        } else {
            System.out.print("Enter correct response (a: True/b: False): ");
            String correctResponse = inputDriver.readStringInput(1);
            correctResponse = correctResponse.replace("\n", "");
            if (validChoices.contains((correctResponse))) {
                ResponseCorrectAnswer validResponse = new ResponseCorrectAnswer(correctResponse, true);
                validResponse.addResponseToQuestion(question);
            } else {
                System.out.println("Please enter a valid correct response");
                getCorrectResponse(question);
            }
        }
    }

    @Override
    public void getUserResponse(Question question) {
        ArrayList<String> validChoices = validChoices();
        ArrayList<String> multipleResponses = new ArrayList<>();
        if (this.numResponses > 1) {
            for (int i = 0; i < this.numResponses; i++) {
                boolean checker = true;
                while (checker) {
                    System.out.print("Enter response " + (i + 1) + ": ");
                    String response = inputDriver.readStringInput(1);
                    response = response.replace("\n", "");
                    if (validChoices.contains((response))) {
                        multipleResponses.add(response);
                        checker = false;
                    } else {
                        System.out.println("Please enter a valid response");
                    }
                }
            }
            if (!multipleResponses.isEmpty()) {
                ResponseCorrectAnswer validResponse = new ResponseCorrectAnswer(multipleResponses, false);
                validResponse.addMultipleResponsesToQuestion(question);
            }
        } else {
            System.out.print("Enter your response (a:True /b: False) or(a:choice1/b:choice2/c:choice3...): ");
            String response = inputDriver.readStringInput(1);
            response = response.replace("\n", "");
            if (validChoices.contains((response))) {
                ResponseCorrectAnswer validResponse = new ResponseCorrectAnswer(response, false);
                validResponse.addResponseToQuestion(question);
            } else {
                System.out.println("Please enter a valid response");
                getUserResponse(question);
            }
        }
    }

    @Override
    public void displayQuestion() {
        System.out.println(questionNumber + ") " + questionPrompt);
        displayChoices();
        System.out.print("\n");
    }

    public void displayChoices() {
        int possibleChoices = 'a';
        for (int i = 0; i < choices.size(); i++) {
            System.out.println((char) (possibleChoices + i) + ") " + choices.get(i));
        }
    }

    @Override
    public void editQuestion() {
        displayQuestion();
        System.out.print("Do you wish to modify the prompt? (yes or no) ");
        String answer = inputDriver.readStringInput(1);
        answer = answer.replace("\n", "");
        if (answer.equals("yes")) {
            System.out.println(this.questionPrompt);
            System.out.println("Enter new question prompt: ");
            String newPrompt = inputDriver.readStringInput(1);
            newPrompt = newPrompt.replace("\n", "");
            this.questionPrompt = newPrompt;
        }
        System.out.print("Do you wish to modify the choices? (yes or no) ");
        answer = inputDriver.readStringInput(1);
        answer = answer.replace("\n", "");
        if (answer.equals("yes")) {
            System.out.print("Which choice do you want to edit (a = 1, b = 2 ...): \n ");
            displayChoices();
            int choiceToModify = inputDriver.getIntegerInput(this.choices.size());
            System.out.println("Enter new choice: ");
            String newChoice = inputDriver.readStringInput(1);
            newChoice = newChoice.replace("\n", "");
            this.choices.set(choiceToModify - 1, newChoice);
        }
    }

}
