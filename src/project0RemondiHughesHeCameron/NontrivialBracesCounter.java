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

    public NontrivialBracesCounter(){

    }

    public int getNumNontrivialLeftBraces(String program){
        for (int i=1; i<program.length(); i++){
            char currentChar = program.charAt(i);
            char lastChar = program.charAt(i-1);

            if(counting) {
                // count if this is true and not a new line
//            if(currentChar == '\\') {
//
//            }

                // multiline comment
                if (currentChar == '*' && lastChar == '/') {
                    // ignore everything until we find * /
                    this.ignoreTrivialToken = "/*";
                    this.counting = false;

                }
                // single line comment
                else if (currentChar == '/' && lastChar == '/') {
                    // ignore everything until we find \n
                    this.ignoreTrivialToken = "//";
                    this.counting = false;
                }
                // normal string
                else if (currentChar == '"') {
                    // ignore everything until we find another " unless there is breakout character
                    this.ignoreTrivialToken = "\"";
                    this.counting = false;

                }
                // string literal
                else if (currentChar == '\'') {
                    // ignore everything until we find '
                    this.ignoreTrivialToken = "\'";
                    this.counting = false;
                }
                else{
                    if (currentChar == '{'){
                        leftBraceCount++;
                        System.out.println(i);
                    }
                }
            }
            else{
                // multiline comment
                if (currentChar == '/' && lastChar == '*' && ignoreTrivialToken.equals("/*")) {
                    this.ignoreTrivialToken = "";
                    this.counting = true;

                }
                // single line comment
                else if (currentChar == '\n' && ignoreTrivialToken.equals("//")) {
                    this.ignoreTrivialToken = "";
                    this.counting = true;
                }
                // normal string
                else if (currentChar == '"' && ignoreTrivialToken.equals("\"")) {
                    this.ignoreTrivialToken = "";
                    this.counting = true;
                }
                // string literal
                else if (currentChar == '\'' && ignoreTrivialToken.equals("\'")) {
                    this.ignoreTrivialToken = "";
                    this.counting = true;
                }
            }
        }
        return leftBraceCount;
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
