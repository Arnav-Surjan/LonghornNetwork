import java.util.*;

public class GaleShapley {
    public static void assignRoommates(List<UniversityStudent> students) {
        // Map to store the current roommate pairings
        Map<String, String> roommatePairings = new HashMap<>();
        // Map of student names to their preferences for easy lookup
        Map<String, Queue<String>> preferenceMap = new HashMap<>();
        // Set to track unpaired students
        Set<String> unpairedStudents = new HashSet<>();

        // Initialize data structures
        for (UniversityStudent student : students) {
            String name = student.name;
            List<String> preferences = student.roommatePreferences;

            unpairedStudents.add(name);
            preferenceMap.put(name, new LinkedList<>(preferences)); // Queue for preferences
        }

        // Main Gale-Shapley algorithm
        while (!unpairedStudents.isEmpty()) {
            // Get the next unpaired student
            String proposer = unpairedStudents.iterator().next();
            Queue<String> preferences = preferenceMap.get(proposer);

            if (preferences == null || preferences.isEmpty()) {
                // No preferences left, remove from unpaired
                unpairedStudents.remove(proposer);
                continue;
            }

            // Propose to the top preference
            String preferredRoommate = preferences.poll();

            if (!roommatePairings.containsKey(preferredRoommate)) {
                // Preferred roommate is unpaired, form a pairing
                roommatePairings.put(proposer, preferredRoommate);
                roommatePairings.put(preferredRoommate, proposer);
                unpairedStudents.remove(proposer);
                unpairedStudents.remove(preferredRoommate);
            } else {
                // Preferred roommate is already paired
                String currentRoommate = roommatePairings.get(preferredRoommate);
                List<String> preferredRoommatePrefs = students.stream()
                        .filter(s -> s.name.equals(preferredRoommate))
                        .findFirst()
                        .map(s -> s.roommatePreferences)
                        .orElse(new ArrayList<>());

                // Check if preferredRoommate prefers proposer over currentRoommate
                if (preferredRoommatePrefs.indexOf(proposer) < preferredRoommatePrefs.indexOf(currentRoommate)) {
                    // Preferred roommate prefers the proposer, update pairing
                    roommatePairings.put(proposer, preferredRoommate);
                    roommatePairings.put(preferredRoommate, proposer);

                    // Unpair the current roommate
                    roommatePairings.remove(currentRoommate);
                    unpairedStudents.add(currentRoommate);
                    unpairedStudents.remove(proposer);
                }
            }
        }

        // Print the roommate pairings
        System.out.println("Roommate Pairings:");
        Set<String> printed = new HashSet<>();
        for (Map.Entry<String, String> entry : roommatePairings.entrySet()) {
            String student1 = entry.getKey();
            String student2 = entry.getValue();
            if (!printed.contains(student1) && !printed.contains(student2)) {
                System.out.println(student1 + " ↔ " + student2);
                printed.add(student1);
                printed.add(student2);
            }
        }

        // Students left unpaired
        System.out.println("\nUnpaired Students:");
        for (String student : unpairedStudents) {
            System.out.println(student);
        }
    }
}
