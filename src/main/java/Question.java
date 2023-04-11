import input.ConsoleInput;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Question implements Serializable {

    private static final input.InputDriver inputDriver = new ConsoleInput();
    private static final long serialVersionUID = 1L;
    public String questionPrompt;
    public int questionNumber;
    public ArrayList<String> response;
    public ArrayList<String> correctResponse;
    public ArrayList<String> totalResponses;

    public Question(String prompt, int qNumber) {
        this.questionPrompt = prompt;
        this.questionNumber = qNumber;
        this.response = new ArrayList<>();
        this.correctResponse = new ArrayList<>();
        this.totalResponses = new ArrayList<>();
    }

    public void editQuestion() {
        displayQuestion();
        System.out.print("Do you wish to modify the prompt? (yes or no)");
        String answer = inputDriver.readStringInput(1);
        answer = answer.replace("\n", "");
        if (answer.equals("yes")) {
            System.out.println("Enter new question prompt: ");
            String newPrompt = inputDriver.readStringInput(1);
            newPrompt = newPrompt.replace("\n", "");
            this.questionPrompt = newPrompt;
        }
    }

    public HashMap<String, Integer> countOccurence(ArrayList<String> totalResponses) {
        HashMap<String, Integer> occurCountMap = new HashMap<String, Integer>();
        for (String ans : totalResponses) {
            if (occurCountMap.containsKey(ans)) {
                occurCountMap.put(ans, occurCountMap.get(ans) + 1);
            } else {
                occurCountMap.put(ans, 1);
            }
        }
        return occurCountMap;
    }

    public void tabulateQuestion() {
        HashMap<String, Integer> occureCount = countOccurence(this.totalResponses);
        for (Map.Entry entry : occureCount.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public void displayQuestion() {
        System.out.println(questionNumber + ") " + questionPrompt);
    }

    public int gradeQuestion() {
        if (response.equals(correctResponse)) {
            return 1;
        } else {
            return 0;
        }
    }


    public void getCorrectResponse(Question question) {
        System.out.println("Enter correct response");
        String correctResponse = inputDriver.readStringInput(1);
        correctResponse = correctResponse.replace("\n", "");
        ResponseCorrectAnswer validCorrectResponse = new ResponseCorrectAnswer(correctResponse, true);
        validCorrectResponse.addResponseToQuestion(question);
    }

    public void getUserResponse(Question question) {
        System.out.print("Enter your response: ");
        String response = inputDriver.readStringInput(1);
        response = response.replace("\n", "");
        ResponseCorrectAnswer validResponse = new ResponseCorrectAnswer(response, false);
        validResponse.addResponseToQuestion(question);
    }

    public void displayCorrectResponse() {
        System.out.println(" The correct answer is : " + correctResponse);
        System.out.println("\n");
    }

    public void editTestQuestion(Question q) {
        displayQuestion();
        System.out.print("Do you wish to modify the prompt? (yes or no)");
        String answer = inputDriver.readStringInput(1);
        answer = answer.replace("\n", "");
        if (answer.equals("yes")) {
            System.out.println("Enter new question prompt: ");
            String newPrompt = inputDriver.readStringInput(1);
            newPrompt = newPrompt.replace("\n", "");
            this.questionPrompt = newPrompt;
        }
        System.out.print(" Do you wish to modify the correct Answer? (yes or no)");
        String answer2 = inputDriver.readStringInput(1);
        answer2 = answer2.replace("\n", "");
        if (answer2.equals("yes")) {
            getCorrectResponse(q);
        }
    }
}
