package core.repository;

import core.model.libro.Book;
import core.model.persona.Author;
import core.model.editoriales.Publisher;
import core.model.utils.SerializationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookRepository {

    private static BookRepository instance;
    private final ArrayList<Book> books;

    private BookRepository() {
        this.books = new ArrayList<>();
    }

    public static synchronized BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    public synchronized boolean add(Book book) {
        if (exists(book.getIsbn())) {
            return false;
        }

        int i = 0;
        while (i < books.size() && books.get(i).getIsbn().compareToIgnoreCase(book.getIsbn()) < 0) {
            i++;
        }
        books.add(i, book);
        return true;
    }

    public synchronized Book findByIsbn(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equalsIgnoreCase(isbn)) {
                return SerializationUtils.deepCopy(b);
            }
        }
        return null;
    }

    public synchronized boolean exists(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equalsIgnoreCase(isbn)) {
                return true;
            }
        }
        return false;
    }

    public synchronized ArrayList<Book> getAllOrderedByIsbn() {
        ArrayList<Book> result = new ArrayList<>();
        for (Book b : books) {
            result.add(SerializationUtils.deepCopy(b));
        }
        return result;
    }

    public synchronized List<Book> getBooksByFormat(String format) {
        ArrayList<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (book.getFormat().equalsIgnoreCase(format)) {
                result.add(SerializationUtils.deepCopy(book));
            }
        }

        result.sort((a, b) -> a.getIsbn().compareToIgnoreCase(b.getIsbn()));
        return result;
    }

    public synchronized List<Book> getBooksByAuthor(Author author) {
        ArrayList<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthors().contains(author)) {
                result.add(SerializationUtils.deepCopy(book));
            }
        }

        result.sort((a, b) -> a.getIsbn().compareToIgnoreCase(b.getIsbn()));
        return result;
    }

    public synchronized List<Author> getTopAuthorsByDifferentPublishers() {
        Map<Author, Set<Publisher>> authorPublisherMap = new HashMap<>();

        for (Book book : books) {
            for (Author author : book.getAuthors()) {
                authorPublisherMap.putIfAbsent(author, new HashSet<>());
                authorPublisherMap.get(author).add(book.getPublisher());
            }
        }

        int max = 0;
        List<Author> result = new ArrayList<>();

        for (Map.Entry<Author, Set<Publisher>> entry : authorPublisherMap.entrySet()) {
            int count = entry.getValue().size();

            if (count > max) {
                max = count;
                result.clear();
                result.add(SerializationUtils.deepCopy(entry.getKey()));
            } else if (count == max) {
                result.add(SerializationUtils.deepCopy(entry.getKey()));
            }
        }

        result.sort((a, b) -> Long.compare(a.getId(), b.getId()));
        return result;
    }

    public synchronized ArrayList<Book> getAll() {
        ArrayList<Book> result = new ArrayList<>();
        for (Book b : books) {
            result.add(SerializationUtils.deepCopy(b));
        }
        return result;
    }
}
