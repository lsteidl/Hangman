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
        System.out.println(x);
        int index = (int) (x * 58109);
        System.out.println(index);
        index--; // shift to 0 based indexing
        // creating File instance to reference text file
        File text = new File("wordlist.txt");
        Scanner reader;
        String choice = "";
        // get chosen word from file
        try {
            int count = 0;
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
        List<String> wrong = new ArrayList<String>(); // maintain list of incorrect guesses
        // scanner for user input
        final Scanner input = new Scanner(System.in); // scanner for user input
        int length = choice.length();
        char[] word = choice.toCharArray(); // actual word
        char hiddenWord[] = new char[length]; // word with hidden letters
        for (int i = 0; i < length; i++) {
            hiddenWord[i] = '*'; // fill each letter space with *
        }
        int count = 0; // track correct guesses
        boolean solved = false;
        char letter = 1; // holds user guess
        while (!solved) { // loop until solved
            printWord(hiddenWord); // display word to be guessed
            printIncorrect(wrong);
            System.out.print("Enter guess...");
            if (input.hasNext()) {
                String guess = input.next(); // get input as string
                letter = guess.charAt(0); // convert input to char
                System.out.println("You guessed " + letter);
                // check guess
                boolean match = false;
                for (int i = 0; i < hiddenWord.length; i++) {
                    if (word[i] == letter) { // search for guess in word
                        match = true;
                        count++; // increment correct count
                        hiddenWord[i] = letter; // update hidden word with correct guess
                    }
                }
                // System.out.println("Count= " + count);
                if (match) {
                    System.out.println("Correct!");
                } else {
                    wrong.add(String.valueOf(letter));
                    System.out.println("Wrong!");
                }

            }
            if (count == word.length) {
                solved = true;
            }
        }
        printWord(hiddenWord);
        System.out.println("Solved!");
        System.out.println();

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