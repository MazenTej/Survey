import input.ConsoleInput;
import output.ConsoleOutput;
import output.OutputMenuDriver;

import java.io.Serializable;
import java.util.ArrayList;

public class Test extends Survey implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final OutputMenuDriver outputMenuDriver = new ConsoleOutput();
    private static final input.InputDriver inputDriver = new ConsoleInput();
    private static final SerializeHelper serializeHelper = new SerializeHelper();


    public Test(ArrayList<Question> listOfQuestions, String name) {
        super(listOfQuestions, name);
    }

    public static Test createTest() {
        Question initialQuestion;
        Question question = null;
        ArrayList<Question> questions = new ArrayList<Question>();
        ArrayList<String> createTestMenu = new ArrayList<>();
        createTestMenu.add("Add a new T/F question");
        createTestMenu.add("Add a multiple-choice question");
        createTestMenu.add("Add a new short answer question");
        createTestMenu.add("Add a essay question");
        createTestMenu.add("Add a new date question");
        createTestMenu.add("Add a new matching question");
        createTestMenu.add("Return to previous menu");
        ArrayList<String> firstOptions = new ArrayList<String>();
        ArrayList<String> secondOptions = new ArrayList<String>();

        System.out.print("Enter the name of this test: ");
        String testName = inputDriver.readStringInput(1);
        testName = testName.replace("\n", "");
        System.out.print("Please enter the number of questions in the test: ");
        int numberOfQuestions = inputDriver.getIntegerInput(15);
        int questionNumber = 1;
        while (questionNumber < numberOfQuestions + 1) {
            outputMenuDriver.displayNumberedOptions(createTestMenu);
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
                question.getCorrectResponse(question);
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
                question.getCorrectResponse(question);
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
                question.getCorrectResponse(question);
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
                question.getCorrectResponse(question);
            } else if (typeOfQuestion == 7) {
                return null;
            }
            questions.add(question);
            questionNumber++;

        }
        Test test = new Test(questions, testName);
        return test;
    }

    public static void displayTest(Test loadedTest) {
        if (loadedTest == null) {
            System.out.println("You must load a survey to display it");
        } else {
            loadedTest.display();
        }
    }


    public static Survey loadTest() {
        ArrayList<String> loadableTests = serializeHelper.getAllFiles();

        if (loadableTests.size() == 0) {
            System.out.println("There are no loadable Tests");
            return null;
        }
        outputMenuDriver.displayNumberedOptions(loadableTests);
        System.out.print("Select a Test: ");
        int selectedTest = inputDriver.getIntegerInput(loadableTests.size());

        Survey loadedTest = Test.loadFile(loadableTests.get(selectedTest - 1));

        return loadedTest;
    }

    public static Test loadFile(String path) {
        Test loaded = (Test) serializeHelper.deserialize(path);
        return loaded;
    }

    public static void takeTest(Test loadedTest) {
        if (loadedTest == null) {
            System.out.println("Please load the test you wish to take");
        } else {
            System.out.print("Enter a name for your response of the test: ");
            String responseName = inputDriver.readStringInput(1);
            responseName = responseName.replace("\n", "");
            for (Question question : loadedTest.questions) {
                question.displayQuestion();
                question.getUserResponse(question);
            }
            Test duplicate = new Test(loadedTest.questions, responseName);
            saveTest(duplicate);
            saveTest(loadedTest);
        }
    }

    public static void tabulateTest(Test loadedTest) {
        if (loadedTest == null) {
            System.out.println("Please load a test to tabulate it");
        } else {
            loadedTest.tabulate();
        }
    }

    public static void gradeTest(Test loadedTest) {
        if (loadedTest == null) {
            System.out.println("Please load the test you wish to grade");
        } else {
            int sum = 0;
            int ungradableQuestions = 0;
            int gradableQuestions = 0;
            int correctAnswers = 0;
            for (Question question : loadedTest.questions) {
                if (question.gradeQuestion() == 1) {
                    sum = sum + 100 / (loadedTest.questions.size());
                    gradableQuestions += 1;
                    correctAnswers += 1;
                } else if (question.gradeQuestion() == 0) {
                    gradableQuestions += 1;
                } else if (question.gradeQuestion() == 2) {
                    ungradableQuestions += 1;
                }
            }
            System.out.println("You scored " + sum + " out of a 100 as you had " + correctAnswers + " gradable questions correct out of " + gradableQuestions + " . There was " + ungradableQuestions + " ungradable questions in the test");
        }
    }

    public static void saveTest(Test loadedTest) {
        if (loadedTest == null) {
            System.out.println("You need to load a test to save it");
        } else {
            Test.saveFile(loadedTest);
        }
    }

    public static void editTest(Test loadedTest) {
        if (loadedTest == null) {
            System.out.println("You must load a survey to edit it");
        } else {
            loadedTest.display();
            ArrayList<String> editMenu = new ArrayList<>();
            editMenu.add("Edit an existing question");
            editMenu.add("Add a new question");
            editMenu.add("Remove an existing question");
            outputMenuDriver.displayNumberedOptions(editMenu);
            System.out.print("Select a choice: ");
            int menuChoice = inputDriver.getIntegerInput(editMenu.size());
            if (menuChoice == 1) {
                System.out.print("Select a question to edit: ");
                int qNumber = inputDriver.getIntegerInput(loadedTest.questions.size());
                loadedTest.ediTestQuestion(qNumber);
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
                String qPrompt = createPrompt(loadedTest.questions.size() + 1);
                initialQuestion = new Question(qPrompt, loadedTest.questions.size() + 1);
                outputMenuDriver.displayNumberedOptions(questionMenu);
                System.out.print("Select the type of question: ");
                int typeOfQuestion = inputDriver.getIntegerInput(questionMenu.size());
                if (typeOfQuestion == 1) {
                    ArrayList<String> TFChoices = new ArrayList<String>();
                    TFChoices.add("True");
                    TFChoices.add("False");
                    question = new TrueFalse(initialQuestion, TFChoices);
                    question.getCorrectResponse(question);
                    loadedTest.questions.add(question);
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
                    question.getCorrectResponse(question);
                    loadedTest.questions.add(question);

                } else if (typeOfQuestion == 3) {
                    System.out.println("Enter number of responses possible :");
                    int partNumber = inputDriver.getIntegerInput(2);
                    System.out.print("Enter the max lines for a response: ");
                    int maxLines = inputDriver.getIntegerInput(5);
                    question = new ShortAnswer(initialQuestion, maxLines, partNumber);
                    loadedTest.questions.add(question);

                } else if (typeOfQuestion == 4) {
                    System.out.println("Enter number of responses possible ");
                    int nbrParts = inputDriver.getIntegerInput(2);
                    question = new Essay(initialQuestion, nbrParts);
                    loadedTest.questions.add(question);
                } else if (typeOfQuestion == 5) {
                    question = new ValidDate(initialQuestion);
                    question.getCorrectResponse(question);
                    loadedTest.questions.add(question);

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
                    question.getCorrectResponse(question);
                    loadedTest.questions.add(question);
                }
            } else if (menuChoice == 3) {
                System.out.print("Select a question to remove: ");
                int qNumber = inputDriver.getIntegerInput(loadedTest.questions.size());
                loadedTest.removeQuestion(qNumber);
            }
        }
    }

    public void displayTestWithCorrectAnswers(Test loadedTest) {
        if (loadedTest == null) {
            System.out.println("You must load a test to display it");
        } else {
            loadedTest.displayWithCorrectAnswers();
        }
    }

    public void displayTestWithoutCorrectAnswer(Test loadedTest) {
        if (loadedTest == null) {
            System.out.println("You must load a test to display it ");
        } else {
            loadedTest.display();
        }
    }

    public void displayWithCorrectAnswers() {
        for (Question question : this.questions) {
            question.displayQuestion();
            if (question.correctResponse.size() > 0) {
                question.displayCorrectResponse();
            }

        }
    }

    public void ediTestQuestion(int qNumber) {
        for (Question q : questions) {
            if (q.questionNumber == qNumber) {
                q.editTestQuestion(q);
            }
        }
    }


}
