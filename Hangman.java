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

        // scanner for user input
        final Scanner input = new Scanner(System.in); // scanner for user input
        int length = choice.length();
        char[] word = choice.toCharArray();
        // for(int i = 0; i < length; i++){
        // System.out.println(word[i]);
        // }
        char hiddenWord[] = new char[length];
        for (int i = 0; i < length; i++) {
            hiddenWord[i] = '*';
            // System.out.print(hiddenWord[i]);
        }
        int count = 0;
        boolean solved = false;
        while (!solved) {
            printWord(hiddenWord);
            System.out.print("Enter guess...");
            if (input.hasNext()) {
                String guess = input.next(); // get input as string
                char letter = guess.charAt(0); // convert input to char
                System.out.println("You guessed " + letter);
                // checkGuess(hiddenWord,word,letter);
                // check guess
                boolean match = false;
                for (int i = 0; i < hiddenWord.length; i++) {
                    if (word[i] == letter) { // search for guess in word
                        match = true;
                        count++; //increment correct count
                        hiddenWord[i] = letter; // update hidden word with correct guess
                    }
                }
                //System.out.println("Count= " + count);
                if(match){
                    System.out.println("Correct!");
                }
                else{
                    System.out.println("Wrong!");
                }

            }
            if(count == word.length){
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

    public static boolean checkGuess(char[] hiddenWord, char[] word, char c) {
        boolean match = false;
        for (int i = 0; i < hiddenWord.length; i++) {
            if (word[i] == c) {
                match = true;
                hiddenWord[i] = c;
            }
        }
        return match;
    }

}