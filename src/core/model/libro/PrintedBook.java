package core.model.libro;

import core.model.persona.Author;
import core.model.editoriales.Publisher;

import java.io.Serializable;
import java.util.ArrayList;

public class PrintedBook extends Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pages;
    private int copies;

    public PrintedBook(String title, ArrayList<Author> authors, String isbn, String genre,
                       String format, double value, Publisher publisher, int pages, int copies) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.pages = pages;
        this.copies = copies;
    }

    public int getPages() {
        return pages;
    }

    public int getCopies() {
        return copies;
    }
}
