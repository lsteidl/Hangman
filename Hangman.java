import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
//   _______
//  |     ||
//  o     ||
// -|-    ||     
// / \    ||
//        ||
//_______||||
//

public class Hangman {
    // Hangman game, takes user input regarding difficulty
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
            // System.out.println(choice); // shows chosen word
            reader.close();
        } catch (FileNotFoundException e) {
            // display error
            System.out.println("File not Found error");
            e.printStackTrace();
        }
        // game set up
        List<String> wrong = new ArrayList<String>(); // maintain list of incorrect guesses
        List<String> correct = new ArrayList<String>(); // maintain list of correct guesses
        int correct_guess = 0; // track correct guesses
        int total_guess = 0; // track total guesses
        int wrong_guess = 0; // track wrong guesses
        int max_wrong = 0; // set by user difficulty input
        boolean solved = false; // game state
        char letter = 1; // holds user guess
        int difficulty = 0; // game mode

        // scanner for user input
        final Scanner input = new Scanner(System.in); // scanner for user input
        // set difficulty
        System.out.println("...NEW GAME...");
        System.out.println("Difficulty level...");
        System.out.println("1) Easy");
        System.out.println("2) Medium");
        System.out.println("3) Hard");
        System.out.println("4) Classic Hangman");
        System.out.print("Decision...");

        // get user difficulty input
        if (input.hasNextInt()) {
            difficulty = input.nextInt();
            // set difficulty by assigning int value
            if (difficulty == 1) {
                max_wrong = 10;
            } else if (difficulty == 2) {
                max_wrong = 5;
            } else if (difficulty == 4) {
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
            if (difficulty == 4) { // if visual game mode
                printMan(wrong_guess);
            }
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
                // check previous guesses, wrong and correct
                if (wrong.contains(String.valueOf(letter)) || correct.contains(String.valueOf(letter))) {
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
                    // check if match or wrong guess
                    if (match) {
                        correct.add(String.valueOf(letter));
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
            if (difficulty == 4) { // if visual game mode
                printMan(wrong_guess);
            }
            System.out.print("Solution is...");
            printAnswer(word);
            System.out.println();
        } else {
            System.out.println("Solved!");
            System.out.println();
        }
        input.close();
    }

    // displays plain text word to user
    public static void printAnswer(char[] word) {
        for (int i = 0; i < word.length; i++) {
            System.out.print(word[i]);
        }
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

    static void printMan(int count) {
        String man[] = new String[6]; // word with hidden letters
        // unchanging parts of man
        man[0] = "   _______";
        man[1] = "  |     ||";
        man[5] = "_______||||";
        if (count == 0) {
            man[2] = "        ||";
            man[3] = "        ||";
            man[4] = "        ||";
        } else if (count == 1) {
            man[2] = "  o     ||";
            man[3] = "        ||";
            man[4] = "        ||";
        } else if (count == 2) {
            man[2] = "  o     ||";
            man[3] = " -      ||";
            man[4] = "        ||";
        } else if (count == 3) {
            man[2] = "  o     ||";
            man[3] = " -|     ||";
            man[4] = "        ||";
        } else if (count == 4) {
            man[2] = "  o     ||";
            man[3] = " -|-    ||";
            man[4] = "        ||";
        } else if (count == 5) {
            man[2] = "  o     ||";
            man[3] = " -|-    ||";
            man[4] = " /      ||";
        } else if (count == 6) {
            man[2] = "  o     ||";
            man[3] = " -|-    ||";
            man[4] = " / \\    ||";

        }

        for (int i = 0; i < man.length; i++) {
            System.out.println(man[i]);
        }
        System.out.println();
    }

}
