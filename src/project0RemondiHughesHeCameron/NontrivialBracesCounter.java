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

    /**
     * Whether read characters are significant.
     */
    private boolean counting = true;

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
        counting = true;
        for (int i=0; i<program.length(); i++){
            char currentChar = program.charAt(i);
            char lastChar = program.charAt(i-1);
            if(counting) {
                this.counting = false;
                // multiline comment
                if (currentChar == '*' && lastChar == '/') {
                    this.ignoreTrivialToken = TrivialCharacters.MULTILINECOMMENT;
                }
                // single line comment
                else if (currentChar == '/' && lastChar == '/') {
                    this.ignoreTrivialToken = TrivialCharacters.SINGLELINECOMMENT;
                }
                // normal string
                else if (currentChar == '"') {
                    this.ignoreTrivialToken = TrivialCharacters.STRING;
                }
                // string literal
                else if (currentChar == '\'') {
                    this.ignoreTrivialToken = TrivialCharacters.CHAR;
                }
                else{
                    if (currentChar == '{'){
                        leftBraceCount++;
                    }
                    this.counting = true;
                }
            }
            else{
                if (currentChar == '/' && lastChar == '*' && ignoreTrivialToken == TrivialCharacters.MULTILINECOMMENT) {
                    resetTrivialTokenAndStartCounting();
                }
                else if (currentChar == '\n' && ignoreTrivialToken == TrivialCharacters.SINGLELINECOMMENT) {
                    resetTrivialTokenAndStartCounting();
                }
                else if (currentChar == '"' && ignoreTrivialToken == TrivialCharacters.STRING && lastChar != '\\') {
                    resetTrivialTokenAndStartCounting();
                }
                else if (currentChar == '\'' && ignoreTrivialToken == TrivialCharacters.CHAR && lastChar != '\\') {
                    resetTrivialTokenAndStartCounting();
                }
            }
        }
        return leftBraceCount;
    }

    private void resetTrivialTokenAndStartCounting(){
        this.ignoreTrivialToken = TrivialCharacters.NONE;
        this.counting = true;
    }

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