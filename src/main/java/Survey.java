import input.ConsoleInput;
import output.ConsoleOutput;
import output.OutputMenuDriver;

import java.io.Serializable;
import java.util.ArrayList;

public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final input.InputDriver inputDriver = new ConsoleInput();
    private static final SerializeHelper serializeHelper = new SerializeHelper();
    private static final OutputMenuDriver outputMenuDriver = new ConsoleOutput();
    public ArrayList<Question> questions;
    public String surveyName;

    public Survey(ArrayList<Question> listOfQuestions, String name) {
        this.questions = listOfQuestions;
        this.surveyName = name;
    }

    public static void saveFile(Survey survey) {
        Survey duplicate = survey;
        serializeHelper.serialize(duplicate, survey.surveyName);
        survey.display();
        System.out.println("Saved");
    }

    public static Survey loadFile(String path) {
        Survey loaded = serializeHelper.deserialize(path);
        return loaded;
    }

    public static String createPrompt(int questionNumber) {
        System.out.println("Enter question " + Integer.toString(questionNumber) + " prompt: ");
        String qPrompt = inputDriver.readStringInput(1);
        qPrompt = qPrompt.replace("\n", "");
        return qPrompt;
    }

    public static Survey createSurvey() {
        Question initialQuestion;
        Question question = null;
        ArrayList<Question> questions = new ArrayList<Question>();
        ArrayList<String> createSurveyMenu = new ArrayList<>();
        createSurveyMenu.add("Add a new T/F question");
        createSurveyMenu.add("Add a multiple-choice question");
        createSurveyMenu.add("Add a new short answer question");
        createSurveyMenu.add("Add a essay question");
        createSurveyMenu.add("Add a new date question");
        createSurveyMenu.add("Add a new matching question");
        createSurveyMenu.add("Return to previous menu");
        ArrayList<String> firstOptions = new ArrayList<String>();
        ArrayList<String> secondOptions = new ArrayList<String>();

        System.out.print("Enter the name of this survey: ");
        String surveyName = inputDriver.readStringInput(1);
        surveyName = surveyName.replace("\n", "");
        System.out.print("Please enter the number of questions in the survey: ");
        int numberOfQuestions = inputDriver.getIntegerInput(15);
        int questionNumber = 1;
        while (questionNumber < numberOfQuestions + 1) {
            outputMenuDriver.displayNumberedOptions(createSurveyMenu);
            System.out.print("Select the type of question: ");
            int typeOfQuestion = inputDriver.getIntegerInput(7);
            if (typeOfQuestion == 1) {
                String qPrompt = createPrompt(questionNumber);
                initialQuestion = new Question(qPrompt, questionNumber);
                ArrayList<String> TFChoices = new ArrayList<String>();
                // True / False will always have these choices
                TFChoices.add("True");
                TFChoices.add("False");
                question = new TrueFalse(initialQuestion, TFChoices);
            } else if (typeOfQuestion == 2) {
                String qPrompt = createPrompt(questionNumber);
                initialQuestion = new Question(qPrompt, questionNumber);
                ArrayList<String> choices = new ArrayList<String>();
                System.out.print("Enter the number of choices: ");
                int numChoices = inputDriver.getIntegerInput(20);
                System.out.print("Enter number of expected responses: ");
                int numResponses = inputDriver.getIntegerInput(numChoices);
                int choiceCounter = 0;
                while (choiceCounter < numChoices) {
                    System.out.println("Please enter choice" + Integer.toString(choiceCounter + 1) + ": ");
                    String choice = inputDriver.readStringInput(1);
                    choice = choice.replace("\n", "");
                    choices.add(choice);
                    choiceCounter++;
                }
                question = new MultipleChoice(initialQuestion, choices, numResponses);
            } else if (typeOfQuestion == 3) {
                String qPrompt = createPrompt(questionNumber);
                initialQuestion = new Question(qPrompt, questionNumber);
                System.out.println("Enter number of responses possible :");
                int partNumber = inputDriver.getIntegerInput(2);
                System.out.print("Enter the max lines for a response: ");
                int maxLines = inputDriver.getIntegerInput(5);
                question = new ShortAnswer(initialQuestion, maxLines, partNumber);
            } else if (typeOfQuestion == 4) {
                System.out.println("Enter question" + Integer.toString(questionNumber) + " prompt: ");
                String qPrompt = inputDriver.readStringInput(1);
                qPrompt = qPrompt.replace("\n", "");
                initialQuestion = new Question(qPrompt, questionNumber);
                System.out.println("Enter number of responses possible ");
                int nbrParts = inputDriver.getIntegerInput(5);
                question = new Essay(initialQuestion, nbrParts);
            } else if (typeOfQuestion == 5) {
                System.out.println("Enter question" + Integer.toString(questionNumber) + " prompt: ");
                String qPrompt = inputDriver.readStringInput(1);
                qPrompt = qPrompt.replace("\n", "");
                initialQuestion = new Question(qPrompt, questionNumber);
                question = new ValidDate(initialQuestion);
            } else if (typeOfQuestion == 6) {
                System.out.println("Enter question" + Integer.toString(questionNumber) + " prompt: ");
                String qPrompt = inputDriver.readStringInput(1);
                qPrompt = qPrompt.replace("\n", "");
                initialQuestion = new Question(qPrompt, questionNumber);
                System.out.println("Enter the number of choices for your matching question ");
                int numOptions = inputDriver.getIntegerInput(10);
                int optionCounter = 1;
                while (optionCounter < numOptions + 1) {
                    System.out.println("Please enter first group choice#" + Integer.toString(optionCounter) + ": ");
                    String choice = inputDriver.readStringInput(1);
                    choice = choice.replace("\n", "");
                    firstOptions.add(choice);
                    optionCounter++;
                }
                optionCounter = 1;
                while (optionCounter < numOptions + 1) {
                    System.out.println("Please enter second group choice#" + Integer.toString(optionCounter) + ": ");
                    String choice = inputDriver.readStringInput(1);
                    choice = choice.replace("\n", "");
                    secondOptions.add(choice);
                    optionCounter++;
                }
                question = new Matching(initialQuestion, firstOptions, secondOptions, numOptions);
            } else if (typeOfQuestion == 7) {
                return null;
            }
            questions.add(question);
            questionNumber++;

        }
        Survey survey = new Survey(questions, surveyName);
        return survey;
    }

    public static void displaySurvey(Survey loadedSurvey) {
        if (loadedSurvey == null) {
            System.out.println("You must load a survey to display it");
        } else {
            loadedSurvey.display();
        }
    }

    public static void editSurvey(Survey loadedSurvey) {
        if (loadedSurvey == null) {
            System.out.println("You must load a survey to edit it");
        } else {
            loadedSurvey.display();
            ArrayList<String> editMenu = new ArrayList<>();
            editMenu.add("Edit an existing question");
            editMenu.add("Add a new question");
            editMenu.add("Remove an existing question");
            outputMenuDriver.displayNumberedOptions(editMenu);
            System.out.print("Select a choice: ");
            int menuChoice = inputDriver.getIntegerInput(editMenu.size());
            if (menuChoice == 1) {
                System.out.print("Select a question to edit: ");
                int qNumber = inputDriver.getIntegerInput(loadedSurvey.questions.size());
                loadedSurvey.editSurvey(qNumber);
            } else if (menuChoice == 2) {
                Question initialQuestion;
                Question question;
                ArrayList<String> choices = new ArrayList<String>();
                ArrayList<String> firstOptions = new ArrayList<String>();
                ArrayList<String> secondOptions = new ArrayList<String>();
                ArrayList<String> questionMenu = new ArrayList<>();
                questionMenu.add("Add a new T/F question");
                questionMenu.add("Add a multiple-choice question");
                questionMenu.add("Add a new short answer question");
                questionMenu.add("Add a essay question");
                questionMenu.add("Add a new date question");
                questionMenu.add("Add a new matching question");
                System.out.println();
                String qPrompt = createPrompt(loadedSurvey.questions.size() + 1);
                initialQuestion = new Question(qPrompt, loadedSurvey.questions.size() + 1);
                outputMenuDriver.displayNumberedOptions(questionMenu);
                System.out.print("Select the type of question: ");
                int typeOfQuestion = inputDriver.getIntegerInput(questionMenu.size());
                if (typeOfQuestion == 1) {
                    ArrayList<String> TFChoices = new ArrayList<String>();
                    TFChoices.add("True");
                    TFChoices.add("False");
                    question = new TrueFalse(initialQuestion, TFChoices);
                    loadedSurvey.questions.add(question);
                } else if (typeOfQuestion == 2) {
                    System.out.print("Enter the number of choices: ");
                    int numChoices = inputDriver.getIntegerInput(20);
                    System.out.print("Enter number of expected responses: ");
                    int numResponses = inputDriver.getIntegerInput(numChoices);
                    int choiceCounter = 0;
                    while (choiceCounter < numChoices) {
                        System.out.println("Please enter choice" + Integer.toString(choiceCounter + 1) + ": ");
                        String choice = inputDriver.readStringInput(1);
                        choice = choice.replace("\n", "");
                        choices.add(choice);
                        choiceCounter++;
                    }
                    question = new MultipleChoice(initialQuestion, choices, numResponses);
                    loadedSurvey.questions.add(question);

                } else if (typeOfQuestion == 3) {
                    System.out.println("Enter number of responses possible :");
                    int partNumber = inputDriver.getIntegerInput(2);
                    System.out.print("Enter the max lines for a response: ");
                    int maxLines = inputDriver.getIntegerInput(5);
                    question = new ShortAnswer(initialQuestion, maxLines, partNumber);
                    loadedSurvey.questions.add(question);

                } else if (typeOfQuestion == 4) {
                    System.out.println("Enter number of responses possible ");
                    int nbrParts = inputDriver.getIntegerInput(2);
                    question = new Essay(initialQuestion, nbrParts);
                    loadedSurvey.questions.add(question);
                } else if (typeOfQuestion == 5) {
                    question = new ValidDate(initialQuestion);
                    loadedSurvey.questions.add(question);

                } else if (typeOfQuestion == 6) {
                    System.out.println("Enter the number of choices for matching question ");
                    int numOptions = inputDriver.getIntegerInput(10);
                    int optionCounter = 1;
                    while (optionCounter < numOptions + 1) {
                        System.out.println("Please enter first group choice#" + Integer.toString(optionCounter) + ": ");
                        String choice = inputDriver.readStringInput(1);
                        choice = choice.replace("\n", "");
                        firstOptions.add(choice);
                        optionCounter++;
                    }
                    optionCounter = 1;
                    while (optionCounter < numOptions + 1) {
                        System.out.println("Please enter second group choice#" + Integer.toString(optionCounter) + ": ");
                        String choice = inputDriver.readStringInput(1);
                        choice = choice.replace("\n", "");
                        secondOptions.add(choice);
                        optionCounter++;
                    }
                    question = new Matching(initialQuestion, firstOptions, secondOptions, numOptions);
                    loadedSurvey.questions.add(question);
                }
            } else if (menuChoice == 3) {
                System.out.print("Select a question to remove: ");
                int qNumber = inputDriver.getIntegerInput(loadedSurvey.questions.size());
                loadedSurvey.removeQuestion(qNumber);
            }
        }
    }

    public static Survey loadSurvey() {
        ArrayList<String> loadableSurveys = serializeHelper.getAllFiles();

        if (loadableSurveys.size() == 0) {
            System.out.println("There are no loadable surveys");
            return null;
        }
        outputMenuDriver.displayNumberedOptions(loadableSurveys);
        System.out.print("Select a survey: ");
        int selectedSurvey = inputDriver.getIntegerInput(loadableSurveys.size());

        Survey loadedSurvey = Survey.loadFile(loadableSurveys.get(selectedSurvey - 1));

        return loadedSurvey;
    }

    public static void saveSurvey(Survey loadedSurvey) {
        if (loadedSurvey == null) {
            System.out.println("You need to load a survey to save it");
        } else {
            Survey.saveFile(loadedSurvey);
        }
    }

    public static void takeSurvey(Survey loadedSurvey) {
        if (loadedSurvey == null) {
            System.out.println("Please load the survey you wish to take");
        } else {
            System.out.print("Enter a name for your response survey: ");
            String responseName = inputDriver.readStringInput(1);
            responseName = responseName.replace("\n", "");
            for (Question question : loadedSurvey.questions) {
                question.displayQuestion();
                question.getUserResponse(question);
            }
            Survey duplicate = new Survey(loadedSurvey.questions, responseName);
            saveSurvey(duplicate);
            saveSurvey(loadedSurvey);
        }
    }

    public static void tabulateSurvey(Survey loadedSurvey) {
        if (loadedSurvey == null) {
            System.out.println("Please load a survey to tabulate it");
        } else {
            loadedSurvey.tabulate();
        }
    }


    public void tabulate() {
        for (Question question : this.questions) {
            question.displayQuestion();
            question.tabulateQuestion();
        }
    }

    public void display() {
        for (Question question : this.questions) {
            question.displayQuestion();

        }
    }

    public void editSurvey(int qNumber) {
        for (Question x : questions) {
            if (x.questionNumber == qNumber) {
                x.editQuestion();
            }
        }
    }

    public void removeQuestion(int qNumber) {
        this.questions.remove(qNumber - 1);
    }

}
