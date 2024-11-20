import java.util.*;

public class UniversityStudent extends Student {
    public UniversityStudent(String name, int age, String gender, int year, String major, double gpa, List<String> roommatePreferences, List<String> previousInternships) {
        super(name, age, gender, year, major, gpa, roommatePreferences, previousInternships);
    }

    @Override
    public int calculateConnectionStrength(Student other) {
        return 0;
    }
}

