package core.model.persona;

import core.model.libro.Book;

import java.io.Serializable;
import java.util.ArrayList;

public class Author extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ArrayList<Book> books;

    public Author(long id, String firstName, String lastName) {
        super(id, firstName, lastName);
        this.books = new ArrayList<>();
    }

    public ArrayList<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
        }
    }

    public int getBookCount() {
        return books.size();
    }
}

