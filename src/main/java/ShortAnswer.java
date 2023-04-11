import input.ConsoleInput;

public class ShortAnswer extends Essay {

    private static final input.InputDriver inputDriver = new ConsoleInput();
    private static final long serialVersionUID = 1L;
    public int maxLength;

    public ShortAnswer(Question question, int maxResponseLength, int nbrParts) {
        super(question, nbrParts);
        this.maxLength = maxResponseLength;
    }

    @Override
    public void getUserResponse(Question question) {
        int possibleChoice = 'a';
        if (nbrParts > 1) {
            for (int i = 0; i < nbrParts; i++) {
                System.out.print("Enter your response " + (char) (possibleChoice + i) + ": ");
                String response = inputDriver.readStringInput(maxLength);
                response = response.replace("\n", "");
                ResponseCorrectAnswer validResponse = new ResponseCorrectAnswer(response, false);
                validResponse.addResponseToQuestion(question);
            }
        } else {
            System.out.print("Enter your response: ");
            String response = inputDriver.readStringInput(maxLength);
            response = response.replace("\n", "");
            ResponseCorrectAnswer validResponse = new ResponseCorrectAnswer(response, false);
            validResponse.addResponseToQuestion(question);
        }
    }
}
