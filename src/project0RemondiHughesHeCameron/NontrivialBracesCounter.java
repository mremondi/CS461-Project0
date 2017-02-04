package project0RemondiHughesHeCameron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class NontrivialBracesCounter{

//    private enum characters{
//        MULTILINECOMMENT, SINGLELINECOMMENT, STRING, CHAR
//    }

    private int leftBraceCount = 0;
    private boolean counting = true;
    private String ignoreTrivialToken = "";

    public NontrivialBracesCounter(){}

    public int getNumNontrivialLeftBraces(String program){
        for (int i=1; i<program.length(); i++){
            char currentChar = program.charAt(i);
            char lastChar = program.charAt(i-1);

            if(counting) {
                this.counting = false;
                // multiline comment
                if (currentChar == '*' && lastChar == '/') {
                    // ignore everything until we find * /
                    this.ignoreTrivialToken = "/*";
                }
                // single line comment
                else if (currentChar == '/' && lastChar == '/') {
                    // ignore everything until we find \n
                    this.ignoreTrivialToken = "//";
                }
                // normal string
                else if (currentChar == '"') {
                    // ignore everything until we find another " unless there is breakout character
                    this.ignoreTrivialToken = "\"";
                }
                // string literal
                else if (currentChar == '\'') {
                    // ignore everything until we find '
                    this.ignoreTrivialToken = "\'";
                }
                else{
                    if (currentChar == '{'){
                        leftBraceCount++;
                        System.out.println(i);
                    }
                    this.counting = true;
                }
            }
            else{
                // multiline comment
                if (currentChar == '/' && lastChar == '*' && ignoreTrivialToken.equals("/*")) {
                    resetTrivialTokenAndStartCounting();
                }
                // single line comment
                else if (currentChar == '\n' && ignoreTrivialToken.equals("//")) {
                    resetTrivialTokenAndStartCounting();
                }
                // normal string
                else if (currentChar == '"' && ignoreTrivialToken.equals("\"") && lastChar != '\\') {
                    resetTrivialTokenAndStartCounting();
                }
                // string literal
                else if (currentChar == '\'' && ignoreTrivialToken.equals("\'") && lastChar != '\\') {
                    resetTrivialTokenAndStartCounting();
                }
            }
        }
        return leftBraceCount;
    }

    public void resetTrivialTokenAndStartCounting(){
        this.ignoreTrivialToken = "";
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
