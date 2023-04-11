import input.ConsoleInput;

public class Essay extends Question {

    private static final long serialVersionUID = 1L;
    private static final input.InputDriver inputDriver = new ConsoleInput();
    int nbrParts;

    public Essay(Question question, int nbrParts) {
        super(question.questionPrompt, question.questionNumber);
        this.nbrParts = nbrParts;
    }

    @Override
    public void getUserResponse(Question question) {

        int possibleChoice = 'a';
        if (nbrParts > 1) {
            for (int i = 0; i < nbrParts; i++) {
                System.out.print("Enter your response " + (char) (possibleChoice + i) + ": ");
                String response = inputDriver.readStringInput(20);
                response = response.replace("\n", "");
                ResponseCorrectAnswer validResponse = new ResponseCorrectAnswer(response, false);
                validResponse.addResponseToQuestion(question);
            }
        } else {
            System.out.print("Enter your response: ");
            String response = inputDriver.readStringInput(20);
            response = response.replace("\n", "");
            ResponseCorrectAnswer validResponse = new ResponseCorrectAnswer(response, false);
            validResponse.addResponseToQuestion(question);
        }
    }

    @Override
    public void displayQuestion() {
        System.out.println(questionNumber + ") " + questionPrompt);
        System.out.print("\n");
    }

    @Override
    public int gradeQuestion() {
        return 2;
    }

    @Override
    public void editQuestion() {
        displayQuestion();
        System.out.println(this.questionPrompt);
        System.out.print("Do you wish to modify the prompt? (yes or no) ");
        String answer = inputDriver.readStringInput(1);
        answer = answer.replace("\n", "");
        if (answer.equals("yes")) {
            System.out.println("Enter new question prompt: ");
            String newPrompt = inputDriver.readStringInput(1);
            newPrompt = newPrompt.replace("\n", "");
            this.questionPrompt = newPrompt;
        }
        System.out.print("\n");
        System.out.println(" Do you wish to modify the number of possible responses");
        String answer1 = inputDriver.readStringInput(1);
        answer1 = answer1.replace("\n", "");
        if (answer1.equals("yes")) {
            System.out.println("Enter number of responses");
            int nbrResponses = inputDriver.getIntegerInput(2);
            this.nbrParts = nbrResponses;
        }
    }
}
