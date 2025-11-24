package core.model.persona;

import core.model.libro.AudioBook;
import java.io.Serializable;
import java.util.ArrayList;

public class Narrator extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ArrayList<AudioBook> books;

    public Narrator(long id, String firstName, String lastName) {
        super(id, firstName, lastName);
        this.books = new ArrayList<>();
    }

    public ArrayList<AudioBook> getBooks() {
        return new ArrayList<>(books);
    }

    public void addAudioBook(AudioBook book) {
        if (!books.contains(book)) {
            books.add(book);
        }
    }

    public int getAudioBookCount() {
        return books.size();
    }
}
