package core.model.libro;

import core.model.persona.Author;
import core.model.persona.Narrator;
import core.model.editoriales.Publisher;

import java.io.Serializable;
import java.util.List;

public class AudioBook extends Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int duration;
    private final Narrator narrator;

    public AudioBook(
            String title,
            List<Author> authors,
            String isbn,
            String genre,
            String format,
            double value,
            Publisher publisher,
            int duration,
            Narrator narrator
    ) {
        super(title, authors, isbn, genre, format, value, publisher);
        this.duration = duration;
        this.narrator = narrator;
    }

    public int getDuration() {
        return duration;
    }

    public Narrator getNarrator() {
        return narrator;
    }
}


