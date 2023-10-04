import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class GuessNumber {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Welcome to Guess the Number!");
            System.out.println("I'm thinking of a number between 1 and 100.");

            int totalRounds = 3;
            int roundsPlayed = 0;

            while (roundsPlayed < totalRounds) {
                int randomNumber = generateRandomNumber();

                int attempts = 0;
                boolean hasGuessedCorrectly = false;

                while (attempts < 5 && !hasGuessedCorrectly) {
                    System.out.print("Enter your guess: ");
                    String input = reader.readLine();

                    try {
                        int guess = Integer.parseInt(input);
                        attempts++;

                        if (guess < 1 || guess > 100) {
                            System.out.println("Please enter a number between 1 and 100.");
                        } else if (guess < randomNumber) {
                            System.out.println("Try a higher number.");
                        } else if (guess > randomNumber) {
                            System.out.println("Try a lower number.");
                        } else {
                            hasGuessedCorrectly = true;
                            System.out.println("Congratulations! You've guessed the number " + randomNumber
                                    + " correctly in " + attempts + " attempts.");
                        }

                        if (attempts < 5 && !hasGuessedCorrectly) {
                            System.out.println("You have " + (5 - attempts) + " attempts remaining.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }

                if (!hasGuessedCorrectly) {
                    System.out.println(
                            "Sorry, you've used all your attempts. The correct number was " + randomNumber + ".");
                }

                roundsPlayed++;

                if (roundsPlayed < totalRounds) {
                    System.out.println("Round " + roundsPlayed + " of " + totalRounds + " completed.");
                    System.out.println("Let's play the next round!");
                } else {
                    System.out.println("Game over! You've completed all " + totalRounds + " rounds.");
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }
}
