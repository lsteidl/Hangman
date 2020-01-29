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
        String choice;
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

        input.close();
    }

}