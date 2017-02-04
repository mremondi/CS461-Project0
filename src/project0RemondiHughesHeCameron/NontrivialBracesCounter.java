package project0RemondiHughesHeCameron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class NontrivialBracesCounter{

    private enum TrivialCharacters{
        MULTILINECOMMENT, SINGLELINECOMMENT, STRING, CHAR, NONE
    }

    private int leftBraceCount = 0;
    private boolean counting = true;
    private TrivialCharacters ignoreTrivialToken = TrivialCharacters.NONE;

    private int getNumNontrivialLeftBraces(String program){
        for (int i=1; i<program.length(); i++){
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