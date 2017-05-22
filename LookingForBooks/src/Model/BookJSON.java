package Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kayvon Rahimi on 21-5-2017.
 */

//Model class to bind java book to json book.
public class BookJSON {
    @SerializedName("kind")
    private String kind;
    @SerializedName("totalItems")
    private int totalItems;
    @SerializedName("items")
    private List<Book> books;

    public Book getBook() {
        return books.get(0);
    }

    public BookInfo getBookInfo() {
        return books.get(0).getBookInfo();
    }

}
