/**
 * Created by Kayvon Rahimi on 21-5-2017.
 */
package DataAccess;

import Model.BookJSON;
import com.google.gson.Gson;

import java.io.*;
import java.net.*;


public class LookForBookProtocol {
    private static int WAITING = 0;
    private static int CALLED = 1;
    private static int LOOKFORMORE = 2;
    private static int CONTINUE = 3;

    private int state = WAITING;


    public String processInput(String theInput) throws IOException {
        String theOutput = null;

        //Logic at state 0 (startup)
        if (state == WAITING) {
            theOutput = "Enter a valid ISBN to retrieve information about the book.";
            state = CALLED;
        }

        else if (state == CALLED) {
            if (!theInput.isEmpty()) {
                System.out.println("Received ISBN: " + theInput);
                //theInput here is the  ISBN submitted by the client.
                theOutput = getBook(theInput);
                System.out.println("Executed getBook("+theInput+");");

            state = LOOKFORMORE;
            }

            else {
                theOutput = "The ISBN you submitted is not valid. Please check your ISBN and submit again.";
                state = WAITING;
        }

    }

    else if (state == LOOKFORMORE) {
            if (theInput.isEmpty()){
                theOutput = "Do you want to look up more books? [Y/N]";
                state = CONTINUE;
            }
            else {
                theOutput = "The session has been terminated.";

            }
        }
    else if (state == CONTINUE) {
            if(theInput.equalsIgnoreCase("y")){
                theOutput = "We'll continue looking for books.";
                state = WAITING;
            } else {
                theOutput = "The session has been terminated.";
            }
        }
        return theOutput;
    }

    public String getBook(String isbn) throws IOException {
        URL bookInfo = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn);

        InputStream input = bookInfo.openStream();
        Reader inputReader = new InputStreamReader(input, "UTF-8");
        BookJSON JSONres = new Gson().fromJson(inputReader, BookJSON.class);

        String book = "Title: " + JSONres.getBookInfo().getTitle() +
                " Subtitle: " + JSONres.getBookInfo().getSubTitle() +
                //Get author-array, print as String.
                " Author(s): " + JSONres.getBookInfo().getAuthors().toString();

        return book;
    }
}
