import java.util.ArrayList;

public class ResponseCorrectAnswer {

    public String userResponse;
    public boolean isUserCorrectResponse;
    public ArrayList<String> multipleResponses;


    public ResponseCorrectAnswer(String response, boolean isCorrectResponse) {
        this.userResponse = response;
        this.isUserCorrectResponse = isCorrectResponse;

    }

    public ResponseCorrectAnswer(ArrayList<String> responses, boolean isCorrectResponse) {
        this.multipleResponses = responses;
        this.isUserCorrectResponse = isCorrectResponse;

    }

    public void addMultipleResponsesToQuestion(Question question) {
        if (isUserCorrectResponse) {
            question.correctResponse.clear();
            for (String res : multipleResponses) {
                question.correctResponse.add(res);

            }
        } else {
            question.response.clear();
            for (String res : multipleResponses) {
                question.response.add(res);
                question.totalResponses.add(res);
            }
        }

    }


    public void addResponseToQuestion(Question question) {
        if (isUserCorrectResponse) {
            question.correctResponse.clear();
            question.correctResponse.add(this.userResponse);

        } else {
            question.response.clear();
            question.response.add(this.userResponse);
            System.out.println(question.correctResponse);
            question.totalResponses.add(this.userResponse);
        }

    }
}
