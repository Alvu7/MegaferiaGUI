package core.model.editoriales;

import core.model.libro.Book;
import core.model.persona.Manager;
import core.model.stands.Stand;

import java.io.Serializable;
import java.util.ArrayList;

public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nit;
    private String name;
    private String address;
    private Manager manager;
    private ArrayList<Book> books;
    private ArrayList<Stand> stands;

    public Publisher(String nit, String name, String address, Manager manager) {
        this.nit = nit;
        this.name = name;
        this.address = address;
        this.manager = manager;
        this.books = new ArrayList<>();
        this.stands = new ArrayList<>();

        this.manager.setPublisher(this);
    }

    public String getNit() {
        return nit;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Manager getManager() {
        return manager;
    }

    public ArrayList<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public int getBookQuantity() {
        return books.size();
    }

    public int getStandQuantity() {
        return stands.size();
    }

    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
        }
    }

    public void addStand(Stand stand) {
        if (!stands.contains(stand)) {
            stands.add(stand);
        }
    }
}
