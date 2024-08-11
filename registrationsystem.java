import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Course {
    String code;
    String title;
    String description;
    int capacity;
    ArrayList<String> enrolledStudents;

    Course(String code, String title, String description, int capacity) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolledStudents = new ArrayList<>();
    }

    boolean hasSpace() {
        return enrolledStudents.size() < capacity;
    }

    void enrollStudent(String studentID) {
        if (hasSpace()) {
            enrolledStudents.add(studentID);
        }
    }

    void removeStudent(String studentID) {
        enrolledStudents.remove(studentID);
    }

    int slotsAvailable() {
        return capacity - enrolledStudents.size();
    }
}

class Student {
    String id;
    String name;
    ArrayList<Course> courses;

    Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.courses = new ArrayList<>();
    }

    void register(Course course) {
        if (course.hasSpace()) {
            courses.add(course);
            course.enrollStudent(id);
        } else {
            System.out.println("Course " + course.title + " is full.");
        }
    }

    void unregister(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
            course.removeStudent(id);
        } else {
            System.out.println("You are not registered for this course.");
        }
    }
}

public class RegistrationSystem {
    static HashMap<String, Course> courses = new HashMap<>();
    static HashMap<String, Student> students = new HashMap<>();

    public static void main(String[] args) {
        // Initialize courses
        courses.put("CN101", new Course("CN101", "Computer Networks", "Basics of computer networking.", 30));
        courses.put("CAL101", new Course("CAL101", "Calculus", "Fundamentals of calculus.", 25));
        courses.put("DBMS101", new Course("DBMS101", "Database Systems", "Introduction to database management.", 20));
        courses.put("FLAT101", new Course("FLAT101", "Formal Languages", "Study of formal languages and automata.", 20));

        // Initialize students
        students.put("S001", new Student("S001", "Federico"));
        students.put("S002", new Student("S002", "Julian"));
        students.put("S003", new Student("S003", "Lisandro"));
        students.put("S004", new Student("S004", "Lautaro"));
        students.put("S005", new Student("S005", "Eduardo"));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Show Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Unregister from a Course");
            System.out.println("4. View My Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayCourses();
                    break;
                case 2:
                    registerCourse(scanner);
                    break;
                case 3:
                    unregisterCourse(scanner);
                    break;
                case 4:
                    viewCourses(scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void displayCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses.values()) {
            System.out.println(course.code + ": " + course.title);
            System.out.println("  Description: " + course.description);
            System.out.println("  Available Slots: " + course.slotsAvailable());
        }
    }

    static void registerCourse(Scanner scanner) {
        System.out.print("Enter your student ID: ");
        String studentID = scanner.nextLine();
        Student student = students.get(studentID);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code to register: ");
        String courseCode = scanner.nextLine();
        Course course = courses.get(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        student.register(course);
        System.out.println("Successfully registered for " + course.title);
    }

    static void unregisterCourse(Scanner scanner) {
        System.out.print("Enter your student ID: ");
        String studentID = scanner.nextLine();
        Student student = students.get(studentID);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code to unregister: ");
        String courseCode = scanner.nextLine();
        Course course = courses.get(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        student.unregister(course);
        System.out.println("Successfully unregistered from " + course.title);
    }

    static void viewCourses(Scanner scanner) {
        System.out.print("Enter your student ID: ");
        String studentID = scanner.nextLine();
        Student student = students.get(studentID);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Your Enrolled Courses:");
        for (Course course : student.courses) {
            System.out.println(course.code + ": " + course.title);
        }
    }
}
