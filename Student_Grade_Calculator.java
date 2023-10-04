import java.util.Scanner;

public class Student_Grade_Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int totalMarks = 0;
        int totalSubjects = 0;

        System.out.println("Enter marks obtained in each subject (out of 100). Enter -1 to finish.");

        int marks;
        while ((marks = inputMarks(scanner)) != -1) {
            totalMarks += marks;
            totalSubjects++;
        }

        if (totalSubjects > 0) {
            double averagePercentage = calculateAveragePercentage(totalMarks, totalSubjects);
            String grade = calculateGrade(averagePercentage);

            displayResults(totalMarks, averagePercentage, grade);
        }

        scanner.close();
    }

    public static int inputMarks(Scanner scanner) {
        System.out.print("Enter marks for the next subject (or -1 to finish): ");
        int marks = scanner.nextInt();

        if (marks < -1 || marks > 100) {
            System.out.println("Invalid input. Marks should be between 0 and 100.");
            return inputMarks(scanner);
        }

        return marks;
    }

    public static double calculateAveragePercentage(int totalMarks, int totalSubjects) {
        return (double) totalMarks / totalSubjects;
    }

    public static String calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return "A+";
        } else if (averagePercentage >= 80) {
            return "A";
        } else if (averagePercentage >= 70) {
            return "B";
        } else if (averagePercentage >= 60) {
            return "C";
        } else if (averagePercentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void displayResults(int totalMarks, double averagePercentage, String grade) {
        System.out.println("Total Marks: " + totalMarks);
        System.out.println("Average Percentage: " + averagePercentage + "%");
        System.out.println("Grade: " + grade);
    }
}
