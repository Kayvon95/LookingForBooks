/**
 * Created by Kayvon Rahimi on 19-5-2017.
 */
package Model;

import com.google.gson.annotations.SerializedName;

//Model class for book.
public class Book {
    //Serialized with GSON lib.
    @SerializedName("id")
    private String id;
    //Serialized with GSON lib.
    @SerializedName("volumeInfo")
    private BookInfo bookInfo;

    //Get bookinfo belonging to equal isbn.
    public BookInfo getBookInfo() {
        return bookInfo;
    }
}
