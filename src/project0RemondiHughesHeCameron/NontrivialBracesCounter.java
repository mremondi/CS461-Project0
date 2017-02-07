/*
 * File: NontrivialBracesCounter.java
 * Names: Nick Cameron, He He, Phoebe Hughes, Mike Remondi
 * Class: CS 461
 * Project 0
 * Date: February 7
 */
package project0RemondiHughesHeCameron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Provides a mechanism for counting the number of significant left braces: '{'
 * in a valid Java program. 'Significant' here means that if the left brace were
 * replaced with another character, the program would no longer compile.
 *
 * @author Nick Cameron
 * @author He He
 * @author Phoebe Hughes
 * @author Mike Remondi
 */
public class NontrivialBracesCounter{

    /**
     * Provides a set of modes for the traversal of a file, each used as a sentinel
     * that determines that a character or collection of characters to be read is not
     * significant, and more importantly what character or ordered collection of
     * characters must be read before future characters regain significance.
     */
    private enum TraversalMode {
        MULTILINECOMMENT, SINGLELINECOMMENT, STRING, CHAR, COUNTING
    }

    /**
     * Counts and returns the number of significant (defined in class JavaDoc) left
     * braces in the given string.
     *
     * @param program   the string to count significant left braces from
     * @return  the count of significant left braces
     */
    public int getNumNontrivialLeftBraces(String program){
        int leftBraceCount = 0;
        TraversalMode traversalMode = TraversalMode.COUNTING;
        for (int i=1; i<program.length(); i++){
            char currentChar = program.charAt(i);
            char prevChar = program.charAt(i-1);
            if(traversalMode == TraversalMode.COUNTING) {
                // multiline comment
                if (currentChar == '*' && prevChar == '/') {
                    traversalMode = TraversalMode.MULTILINECOMMENT;
                }
                // single line comment
                else if (currentChar == '/' && prevChar == '/') {
                    traversalMode = TraversalMode.SINGLELINECOMMENT;
                }
                // normal string
                else if (currentChar == '"') {
                    traversalMode = TraversalMode.STRING;
                }
                // char
                else if (currentChar == '\'') {
                    traversalMode = TraversalMode.CHAR;
                }
                else if (currentChar == '{'){
                    leftBraceCount++;
                }
            }
            //not currently counting
            else{
                //end of multiline comment
                if (currentChar == '/' && prevChar == '*' &&
                        traversalMode == TraversalMode.MULTILINECOMMENT) {
                    traversalMode = TraversalMode.COUNTING;
                }
                //end of single line comment
                else if (currentChar == '\n' &&
                             traversalMode == TraversalMode.SINGLELINECOMMENT) {
                    traversalMode = TraversalMode.COUNTING;
                }
                //end of string
                else if (currentChar == '"' && prevChar != '\\'&&
                            traversalMode == TraversalMode.STRING) {
                    traversalMode = TraversalMode.COUNTING;
                }
                //end of char
                else if (currentChar == '\'' && prevChar != '\\' &&
                            traversalMode == TraversalMode.CHAR ) {
                    traversalMode = TraversalMode.COUNTING;
                }
            }
        }
        return leftBraceCount;
    }

    /**
     * Tests on a sample file.
     * @param args
     */
    public static void main(String args[]){
        try {
            String contents = new Scanner(new File("HelloWorld.java")).useDelimiter("\\Z").next();
            NontrivialBracesCounter counter = new NontrivialBracesCounter();
            System.out.print("\nShould have 6 nontrivial left braces. Has: ");
            System.out.println(counter.getNumNontrivialLeftBraces(contents));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}