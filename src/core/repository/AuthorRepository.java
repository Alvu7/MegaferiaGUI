package core.repository;

import core.model.persona.Author;
import core.model.utils.SerializationUtils;
import java.util.ArrayList;

public class AuthorRepository {

    private static AuthorRepository instance;
    private final ArrayList<Author> authors;

    private AuthorRepository() {
        this.authors = new ArrayList<>();
    }

    public static synchronized AuthorRepository getInstance() {
        if (instance == null) {
            instance = new AuthorRepository();
        }
        return instance;
    }

    public synchronized boolean add(Author author) {
        if (exists(author.getId())) {
            return false;
        }

        int i = 0;
        while (i < authors.size() && authors.get(i).getId() < author.getId()) {
            i++;
        }
        authors.add(i, author);
        return true;
    }

    public synchronized Author findById(long id) {
        for (Author a : authors) {
            if (a.getId() == id) {
                return SerializationUtils.deepCopy(a);
            }
        }
        return null;
    }

    public synchronized boolean exists(long id) {
        for (Author a : authors) {
            if (a.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public synchronized ArrayList<Author> getAllOrderedById() {
        ArrayList<Author> list = new ArrayList<>();
        for (Author a : authors) {
            list.add(SerializationUtils.deepCopy(a));
        }
        return list;
    }
}
