import java.io.*;
import java.util.ArrayList;


public class SerializeHelper {

    private static final String projectPath = "./";
    private static final String surveyExtension = ".ser";

    public ArrayList<String> getAllFiles() {
        File dir = new File(projectPath);
        ArrayList<String> serializedFiles = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.endsWith(".ser")) {
                serializedFiles.add(fileName.substring(0, fileName.lastIndexOf('.')));
            }
        }
        return serializedFiles;
    }

    public Survey deserialize(String fileName) {
        Survey survey = null;
        try {
            String filePath = fileName.concat(surveyExtension);

            FileInputStream input = new FileInputStream(filePath);
            ObjectInputStream output = new ObjectInputStream(input);
            survey = (Survey) output.readObject();

            input.close();
            output.close();
            System.out.println("Object has been deserialized");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not deserialize file");
            System.out.println(e);
        }
        return survey;
    }

    public void serialize(Object obj, String fileName) {
        String filePath = fileName.concat(surveyExtension);
        try {
            FileOutputStream f = new FileOutputStream(filePath);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(obj);
            o.close();
            f.close();
            System.out.println("File has been successfully serialized");
        } catch (IOException e) {
            System.out.println("Path: " + filePath);
            System.out.println("Error: " + e);
            System.out.println("Could not serialize file");
        }
    }
}
