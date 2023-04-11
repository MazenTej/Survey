import input.ConsoleInput;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidDate extends Question {

    private static final input.InputDriver inputDriver = new ConsoleInput();
    private static final long serialVersionUID = 1L;

    public ValidDate(Question question) {
        super(question.questionPrompt, question.questionNumber);
    }


    @Override
    public void getCorrectResponse(Question question) {
        String correctResponse = "";
        while (true) {
            System.out.print("Enter your correct response: ");
            correctResponse = inputDriver.readStringInput(1);
            correctResponse = correctResponse.replace("\n", "");
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            sdf.setLenient(false);
            try {
                Date valid = sdf.parse(correctResponse);
                break;
            } catch (ParseException e) {
                System.out.println("Please enter a valid date that follows the provided format (MM-dd-yyyy)");
            }
        }
        ResponseCorrectAnswer validResposne = new ResponseCorrectAnswer(correctResponse, true);
        validResposne.addResponseToQuestion(question);
    }

    @Override
    public void getUserResponse(Question q) {
        String response = "";
        while (true) {
            System.out.print("Enter your response: ");
            response = inputDriver.readStringInput(1);
            response = response.replace("\n", "");
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            sdf.setLenient(false);
            try {
                Date valid = sdf.parse(response);
                break;
            } catch (ParseException e) {
                System.out.println("Please enter a valid date that follows the provided format (MM-dd-yyyy");
            }
        }
        ResponseCorrectAnswer validResposne = new ResponseCorrectAnswer(response, false);
        validResposne.addResponseToQuestion(q);
    }

    @Override
    public void displayQuestion() {
        System.out.println(questionNumber + ") " + questionPrompt + " (Response must be in MM-DD-YYYY format) \n");
    }


}
