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
 */
public class NontrivialBracesCounter{

    /**
     * Provides a set of modes for the traversal of a file, each used as a sentinel
     * that determines that a character or collection of characters to be read is not
     * significant, and more importantly what character or ordered collection of
     * characters must be read before future characters regain significance.
     */
    private enum TrivialCharacters{
        MULTILINECOMMENT, SINGLELINECOMMENT, STRING, CHAR, NONE
    }

    private boolean counting;

    /**
     * Mode for determining how to enter or exit significance mode.
     */
    private TrivialCharacters ignoreTrivialToken = TrivialCharacters.NONE;

    /**
     * Counts and returns the number of significant (defined in class JavaDoc) left
     * braces in the given string.
     *
     * @param program   the string to count significant left braces from
     * @return  the count of significant left braces
     */
    private int getNumNontrivialLeftBraces(String program){
        int leftBraceCount = 0;
        this.counting = true;
        for (int i=1; i<program.length(); i++){
            char currentChar = program.charAt(i);
            char lastChar = program.charAt(i-1);
            if(counting) {
                // multiline comment
                if (currentChar == '*' && lastChar == '/') {
                    this.setTrivialToken(TrivialCharacters.MULTILINECOMMENT);
                    
                }
                // single line comment
                else if (currentChar == '/' && lastChar == '/') {
                    this.setTrivialToken(TrivialCharacters.SINGLELINECOMMENT);
                }
                // normal string
                else if (currentChar == '"') {
                    this.setTrivialToken(TrivialCharacters.STRING);
                }
                // char
                else if (currentChar == '\'') {
                    this.setTrivialToken(TrivialCharacters.CHAR);
                }
                else if (currentChar == '{'){
                    leftBraceCount++;
                }
            }
            else{
                if (currentChar == '/' && lastChar == '*' && ignoreTrivialToken == TrivialCharacters.MULTILINECOMMENT) {
                    setTrivialToken(TrivialCharacters.NONE);
                }
                else if (currentChar == '\n' && ignoreTrivialToken == TrivialCharacters.SINGLELINECOMMENT) {
                    setTrivialToken(TrivialCharacters.NONE);
                }
                else if (currentChar == '"' && ignoreTrivialToken == TrivialCharacters.STRING && lastChar != '\\') {
                    setTrivialToken(TrivialCharacters.NONE);
                }
                else if (currentChar == '\'' && ignoreTrivialToken == TrivialCharacters.CHAR && lastChar != '\\') {
                    setTrivialToken(TrivialCharacters.NONE);
                }
            }
        }
        return leftBraceCount;
    }

    /**
     * Sets counting appropriately depending on the given TrivialCharacters token.
     */
    private void setTrivialToken(TrivialCharacters token){
        this.ignoreTrivialToken = token;
        if (token.equals(TrivialCharacters.NONE)) {
            this.counting = true;
        } else {
            this.counting = false;
        }
    }

    /**
     * Tests on a sample file.
     * @param args
     */
    public static void main(String args[]){
        try {
            String contents = new Scanner(new File("HelloWorld.java") ).useDelimiter("\\Z").next();
            NontrivialBracesCounter counter = new NontrivialBracesCounter();
            System.out.println(counter.getNumNontrivialLeftBraces(contents));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}