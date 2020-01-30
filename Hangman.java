import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;

public class Hangman {
    // Runs the tests for HW3
    public static void main(final String[] args) {
        // prepare to read wordlist
        // choose random index 1-58109
        double x = Math.random();
        int index = (int) (x * 58109);
        index--; // shift to 0 based indexing
        // creating File instance to reference text file
        File text = new File("wordlist.txt");
        Scanner reader; // wordlist reader
        String choice = ""; // chosen word
        // get chosen word from file
        try {
            int count = 0; // keep track of word index
            reader = new Scanner(text);
            while (reader.hasNextLine() && (count < index)) {
                reader.nextLine();
                count++;
            }
            choice = reader.nextLine();
            System.out.println(choice);
            reader.close();
        } catch (FileNotFoundException e) {
            // display error
            System.out.println("File not Found error");
            e.printStackTrace();
        }
        // game set up
        List<String> wrong = new ArrayList<String>(); // maintain list of incorrect guesses
        int correct_guess = 0; // track correct guesses
        int total_guess = 0; // track total guesses
        int wrong_guess = 0; // track wrong guesses
        int max_wrong = 0; // set by user difficulty input
        boolean solved = false; // game state
        char letter = 1; // holds user guess

        // scanner for user input
        final Scanner input = new Scanner(System.in); // scanner for user input
        // set difficulty
        System.out.println("Difficulty level...");
        System.out.println("1) Easy");
        System.out.println("2) Medium");
        System.out.println("3) Hard");
        System.out.print("Decision...");
        
        // get user difficulty input
        if (input.hasNextInt()) {
            int difficulty = input.nextInt();
            // set difficulty by assigning int value
            if (difficulty == 1) {
                max_wrong = 10;
            } else if (difficulty == 2) {
                max_wrong = 5;
            } else {
                max_wrong = 3;
            }
        }
        System.out.println(); // spacing for easy viewing
        // set up hidden and plain char arrays
        int length = choice.length();
        char[] word = choice.toCharArray(); // actual word
        char hiddenWord[] = new char[length]; // word with hidden letters
        // fill each letter space with *
        for (int i = 0; i < length; i++) {
            hiddenWord[i] = '*';
        }
        while (!solved) { // loop until game state = solved
            printWord(hiddenWord); // display word to be guessed
            if (total_guess > 0) {
                printIncorrect(wrong);
            }
            System.out.print("Enter guess...");
            if (input.hasNext()) {
                String guess = input.next(); // get input as string
                letter = guess.charAt(0); // convert input to char
                total_guess++; // increment total guesses
                System.out.println("You guessed " + letter);
                // check previous guesses
                if (wrong.contains(String.valueOf(letter))) {
                    System.out.println("You guessed " + letter + " already! Try Again.");
                } else {
                    // check for match
                    boolean match = false;
                    for (int i = 0; i < hiddenWord.length; i++) {
                        if (word[i] == letter) { // search for guess in word
                            match = true;
                            correct_guess++; // increment correct count
                            hiddenWord[i] = letter; // update hidden word with correct guess
                        }
                    }
                    // System.out.println("Count= " + count);
                    if (match) {
                        System.out.println("Correct!");
                    } else {
                        wrong.add(String.valueOf(letter));
                        wrong_guess++;
                        System.out.println("Wrong!");
                    }

                }
                if (correct_guess == word.length) {
                    solved = true;
                }
            }
            if (max_wrong < wrong_guess) {
                break;
            }
        }
        System.out.println(); 
        printWord(hiddenWord); // display final state of hidden word 
        // check if game was won
        if (max_wrong < wrong_guess) {
            System.out.println("Too many errors - You Lose!");
        } else { 
            System.out.println("Solved!");
            System.out.println();
        }
        input.close();
    }

    // displays hidden word to user
    //
    public static void printWord(char[] hiddenWord) {
        for (int i = 0; i < hiddenWord.length; i++) {
            System.out.print(hiddenWord[i]);
        }
        System.out.println();
        System.out.println(); // spacing for easy reading
    }

    // print past incorrect guesses
    public static void printIncorrect(List<String> wrong) {
        System.out.print("Previous: ");
        for (int i = 0; i < wrong.size(); i++) {
            System.out.print(wrong.get(i));

            if (i != wrong.size() - 1) { // divide all guesses with commas except last
                System.out.print(", ");
            }
        }
        System.out.println();

    }

}