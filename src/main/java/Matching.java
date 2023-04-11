import input.ConsoleInput;

import java.util.*;

public class Matching extends Question {

    private static final input.InputDriver inputDriver = new ConsoleInput();
    private static final long serialVersionUID = 1L;
    public ArrayList<String> firstOptions;
    public ArrayList<String> secondOptions;
    public int numOptions;


    public Matching(Question question, ArrayList<String> firsttOptions, ArrayList<String> secondOptions, int numOptions) {
        super(question.questionPrompt, question.questionNumber);
        this.firstOptions = firsttOptions;
        this.secondOptions = secondOptions;
        this.numOptions = numOptions;
    }


    @Override
    public void displayQuestion() {
        String output = "";
        System.out.println(questionNumber + ") " + questionPrompt);
        int letters = 'a';
        for (int i = 0; i < firstOptions.size(); i++) {
            String first = (i + 1) + ". " + firstOptions.get(i);
            String second = (char) (letters + i) + ". " + secondOptions.get(i);
            output = output.concat(String.format("%-15s%-15s", first, second)).concat("\n");
        }
        System.out.println(output);
    }

    @Override
    public void tabulateQuestion() {
        List<List<String>> partitions = new LinkedList<List<String>>();
        for (int i = 0; i < totalResponses.size(); i += numOptions) {
            partitions.add(totalResponses.subList(i,
                    Math.min(i + numOptions, totalResponses.size())));
        }

        HashMap<List, Integer> occurCountMap = new HashMap<>();
        for (List<String> arr : partitions) {
            if (occurCountMap.containsKey(arr)) {
                occurCountMap.put(arr, occurCountMap.get(arr) + 1);
            } else {
                occurCountMap.put(arr, 1);
            }
        }
        for (Map.Entry entry : occurCountMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }


    }


    public ArrayList<String> validChoices() {
        ArrayList<String> validChoices = new ArrayList<>();
        int possibleChoice = 'a';
        for (int i = 0; i < firstOptions.size(); i++) {
            char option = (char) (possibleChoice + i);
            String validOption = Character.toString(option);
            validChoices.add(validOption);
        }
        return validChoices;
    }

    @Override
    public void editQuestion() {
        displayQuestion();
        System.out.print("Do you wish to modify the prompt? (yes or no) ");
        String answer = inputDriver.readStringInput(1);
        answer = answer.replace("\n", "");
        if (answer.equals("yes")) {
            System.out.println("Enter new question prompt: ");
            String newPrompt = inputDriver.readStringInput(1);
            newPrompt = newPrompt.replace("\n", "");
            this.questionPrompt = newPrompt;
        }
        System.out.print("Do you wish to edit a group of choices? (yes or no) ");
        answer = inputDriver.readStringInput(1);
        answer = answer.replace("\n", "");
        if (answer.equals("yes")) {
            boolean checker = true;
            while (checker) {
                System.out.print("Which group of choices? (first or second) ");
                answer = inputDriver.readStringInput(1);
                answer = answer.replace("\n", "");
                if (answer.equals("first")) {
                    System.out.print("Please enter the number of the choice you wish to edit for first group (a = 1, b = 2...): ");
                    int choiceToModify = inputDriver.getIntegerInput(this.firstOptions.size());
                    System.out.println("Enter new choice: ");
                    String newChoice = inputDriver.readStringInput(1);
                    newChoice = newChoice.replace("\n", "");
                    this.firstOptions.set(choiceToModify - 1, newChoice);
                    checker = false;
                } else if (answer.equals("second")) {
                    System.out.print("Please enter the number of the choice you wish to edit for second group (a = 1, b = 2, etc.): ");
                    int choiceToModify = inputDriver.getIntegerInput(this.secondOptions.size());
                    System.out.println("Enter new choice: ");
                    String newChoice = inputDriver.readStringInput(1);
                    newChoice = newChoice.replace("\n", "");
                    this.secondOptions.set(choiceToModify - 1, newChoice);
                    checker = false;
                } else {
                    System.out.println("Enter First or Second");
                }
            }
        }
    }

    @Override
    public void getCorrectResponse(Question question) {
        ArrayList<String> validChoices = validChoices();
        ArrayList<String> multipleResponses = new ArrayList<>();
        for (int i = 0; i < firstOptions.size(); i++) {
            while (true) {
                System.out.print("Enter your correct response for " + (i + 1) + "(a:1/b:2/c:3...): ");
                String correctResponse = inputDriver.readStringInput(1);
                correctResponse = correctResponse.replace("\n", "");
                if (validChoices.contains(correctResponse)) {
                    multipleResponses.add(correctResponse);
                    break;
                } else {
                    System.out.println("Please enter a valid correct response");
                }
            }
        }
        ResponseCorrectAnswer validResponse = new ResponseCorrectAnswer(multipleResponses, true);
        validResponse.addMultipleResponsesToQuestion(question);
    }

    @Override
    public void getUserResponse(Question question) {
        ArrayList<String> validChoices = validChoices();
        ArrayList<String> multipleResponses = new ArrayList<>();
        for (int i = 0; i < firstOptions.size(); i++) {
            while (true) {
                System.out.print("Enter your response for " + (i + 1) + ": ");
                String response = inputDriver.readStringInput(1);
                response = response.replace("\n", "");
                if (validChoices.contains(response)) {
                    multipleResponses.add(response);
                    break;
                } else {
                    System.out.println("Please enter a valid response");
                }
            }
        }
        ResponseCorrectAnswer validResponse = new ResponseCorrectAnswer(multipleResponses, false);
        validResponse.addMultipleResponsesToQuestion(question);
    }
}
