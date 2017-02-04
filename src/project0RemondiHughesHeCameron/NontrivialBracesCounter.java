package project0RemondiHughesHeCameron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class NontrivialBracesCounter {

    public NontrivialBracesCounter(){

    }

    public int getNumNontrivialLeftBraces(String program){
        return 0;
    }

    public static void main(String args[]){
        try {
            String contents = new Scanner(new File("HelloWorld.java") ).useDelimiter("\\Z").next();
            NontrivialBracesCounter counter = new NontrivialBracesCounter();
            counter.getNumNontrivialLeftBraces(counter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
