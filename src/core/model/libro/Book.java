package core.model.libro;

import core.model.persona.Author;
import core.model.editoriales.Publisher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String title;
    protected ArrayList<Author> authors;
    protected final String isbn;
    protected String genre;
    protected String format;
    protected double value;
    protected Publisher publisher;

    public Book(String title, List<Author> authors, String isbn, String genre,
                String format, double value, Publisher publisher) {

        this.title = title;

        this.authors = new ArrayList<>(authors);

        this.isbn = isbn;
        this.genre = genre;
        this.format = format;
        this.value = value;
        this.publisher = publisher;

        for (Author autor : this.authors) {
            autor.addBook(this);
        }

        this.publisher.addBook(this);
    }

    public String getTitle() {
        return title;
    }

    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public String getIsbn() {
        return isbn;
    }

    public String getGenre() {
        return genre;
    }

    public String getFormat() {
        return format;
    }

    public double getValue() {
        return value;
    }

    public Publisher getPublisher() {
        return publisher;
    }
}
