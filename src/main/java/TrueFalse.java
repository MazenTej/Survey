import java.util.ArrayList;

public class TrueFalse extends MultipleChoice {

    private static final long serialVersionUID = -4598242833626273003L;
    public ArrayList<String> options;

    public TrueFalse(Question question, ArrayList<String> options) {
        super(question, options, 1);
        this.options = options;
    }

    @Override
    public void displayQuestion() {
        System.out.println(questionNumber + ") " + questionPrompt);
        System.out.println("a) " + options.get(0));
        System.out.println("b) " + options.get(1));
        System.out.print("\n");
    }
}
