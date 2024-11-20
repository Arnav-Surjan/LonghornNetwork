import java.io.*;
import java.util.*;

public class DataParser {
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        List<UniversityStudent> students = new ArrayList<>();
        String name = "";
        int age = 0;
        String gender = "";
        int year = 0;
        String major = "";
        double gpa = 0.0;
        List<String> roommatePreferences = new ArrayList<>();
        List<String> previousInternships = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.equals("Student:")) {
                    if (!name.isEmpty()) {
                        // Create a new UniversityStudent and add to the list
                        students.add(new UniversityStudent(name, age, gender, year, major, gpa,
                                new ArrayList<>(roommatePreferences), new ArrayList<>(previousInternships)));
                        // Reset variables for next student
                        roommatePreferences.clear();
                        previousInternships.clear();
                    }
                    name = "";
                } else if (line.startsWith("Name:")) {
                    name = line.substring("Name:".length()).trim();
                } else if (line.startsWith("Age:")) {
                    age = Integer.parseInt(line.substring("Age:".length()).trim());
                } else if (line.startsWith("Gender:")) {
                    gender = line.substring("Gender:".length()).trim();
                } else if (line.startsWith("Year:")) {
                    year = Integer.parseInt(line.substring("Year:".length()).trim());
                } else if (line.startsWith("Major:")) {
                    major = line.substring("Major:".length()).trim();
                } else if (line.startsWith("GPA:")) {
                    gpa = Double.parseDouble(line.substring("GPA:".length()).trim());
                } else if (line.startsWith("RoommatePreferences:")) {
                    roommatePreferences = Arrays.asList(
                            line.substring("RoommatePreferences:".length()).trim().split(",\\s*"));
                } else if (line.startsWith("PreviousInternships:")) {
                    previousInternships = Arrays.asList(
                            line.substring("PreviousInternships:".length()).trim().split(",\\s*"));
                }
            }

            // Add the last student to the list
            if (!name.isEmpty()) {
                students.add(new UniversityStudent(name, age, gender, year, major, gpa,
                        new ArrayList<>(roommatePreferences), new ArrayList<>(previousInternships)));
            }

            return students;
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }
        return null;
    }
}
