import input.ConsoleInput;
import output.ConsoleOutput;
import output.OutputMenuDriver;

import java.util.ArrayList;

public class Main {


    private static final OutputMenuDriver outputMenuDriver = new ConsoleOutput();
    private static final input.InputDriver inputDriver = new ConsoleInput();

    public static void main(String[] args) {
        Survey survey = null;
        Test test = null;
        ArrayList<String> surveyMenu = new ArrayList<>();
        ArrayList<String> testMenu = new ArrayList<>();
        ArrayList<String> initialMenu = new ArrayList<>();
        initialMenu.add("Survey");
        initialMenu.add("Test");
        surveyMenu.add("Create a new Survey");
        surveyMenu.add("Display an existing Survey");
        surveyMenu.add("Load an existing Survey");
        surveyMenu.add("Save the current Survey");
        surveyMenu.add("Take the current Survey");
        surveyMenu.add("Modifying the current Survey");
        surveyMenu.add("Tabulate a survey");
        surveyMenu.add("Return to previous menu");
        testMenu.add("Create a new Test");
        testMenu.add("Display an existing Test without correct answers");
        testMenu.add("Display an existing Test with correct answers");
        testMenu.add("Load an existing Test");
        testMenu.add("Save the current Test");
        testMenu.add("Take the current Test");
        testMenu.add("Modify the current Test");
        testMenu.add("Grade the current Test");
        testMenu.add("Tabulate the test");
        testMenu.add("Return to previous menu");


        while (true) {
            outputMenuDriver.displayNumberedOptions(initialMenu);
            System.out.println();
            System.out.print("Please select a menu option: ");
            int selectedOption = inputDriver.getIntegerInput(2);
            if (selectedOption == 1) {
                while (true) {
                    outputMenuDriver.displayNumberedOptions(surveyMenu);
                    System.out.print("Please select a menu option: ");
                    int option = inputDriver.getIntegerInput(8);
                    if (option == 1) {
                        survey = survey.createSurvey();
                    } else if (option == 2) {
                        survey.displaySurvey(survey);
                    } else if (option == 3) {
                        survey = survey.loadSurvey();
                    } else if (option == 4) {
                        survey.saveSurvey(survey);
                    } else if (option == 5) {
                        survey.takeSurvey(survey);
                    } else if (option == 6) {
                        survey.editSurvey(survey);
                    } else if (option == 7) {
                        survey.tabulateSurvey(survey);
                    } else if (option == 8) {
                        break;
                    }
                }
            }
            if (selectedOption == 2) {
                while (true) {
                    outputMenuDriver.displayNumberedOptions(testMenu);
                    System.out.print("Please select a menu option: ");
                    int s = inputDriver.getIntegerInput(10);
                    if (s == 1) {
                        test = test.createTest();
                    } else if (s == 2) {
                        test.displayTestWithoutCorrectAnswer(test);
                    } else if (s == 3) {
                        test.displayTestWithCorrectAnswers(test);
                    } else if (s == 4) {
                        test = (Test) test.loadTest();
                    } else if (s == 5) {
                        test.saveTest(test);
                    } else if (s == 6) {
                        test.takeTest(test);
                    } else if (s == 7) {
                        test.editTest(test);
                    } else if (s == 8) {
                        test.gradeTest(test);
                    } else if (s == 9) {
                        test.tabulateTest(test);
                    } else if (s == 10) {
                        break;
                    }
                }
            }

        }
    }
}
