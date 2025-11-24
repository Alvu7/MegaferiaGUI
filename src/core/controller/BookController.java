package core.controller;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.libro.Book;
import core.model.libro.PrintedBook;
import core.model.libro.DigitalBook;
import core.model.libro.AudioBook;
import core.model.persona.Author;
import core.model.persona.Narrator;
import core.model.editoriales.Publisher;
import core.repository.AuthorRepository;
import core.repository.BookRepository;
import core.repository.NarratorRepository;
import core.repository.PublisherRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final NarratorRepository narratorRepository;
    private final PublisherRepository publisherRepository;

    public BookController() {
        this.bookRepository = BookRepository.getInstance();
        this.authorRepository = AuthorRepository.getInstance();
        this.narratorRepository = NarratorRepository.getInstance();
        this.publisherRepository = PublisherRepository.getInstance();
    }

    public Response createPrintedBook(String title, List<Long> authorIds, String isbn, String genre,
                                      double value, String nitPublisher, int pages, int copies) {

        return createBook(title, authorIds, isbn, genre, value, nitPublisher,
                "IMPRESO", pages, 0, copies, null, null);
    }

    public Response createDigitalBook(String title, List<Long> authorIds, String isbn, String genre,
                                      double value, String nitPublisher, String hyperlink) {

        return createBook(title, authorIds, isbn, genre, value, nitPublisher,
                "DIGITAL", 0, 0, 0, null, hyperlink);
    }

    public Response createAudioBook(String title, List<Long> authorIds, String isbn, String genre,
                                    double value, String nitPublisher, long narratorId, int duration) {

        Narrator narrator = narratorRepository.findById(narratorId);
        if (narrator == null) {
            return new Response("Narrador no encontrado", StatusCode.NOT_FOUND);
        }

        return createBook(title, authorIds, isbn, genre, value, nitPublisher,
                "AUDIO", 0, duration, 0, narrator, null);
    }


    private Response createBook(String title, List<Long> authorIds, String isbn, String genre, double value,
                                String nitPublisher, String format, int pages, int duration, int copies,
                                Narrator narrator, String hyperlink) {

        if (title == null || title.isBlank() ||
            isbn == null || isbn.isBlank() ||
            genre == null || genre.isBlank()) {

            return new Response("Todos los campos son obligatorios", StatusCode.BAD_REQUEST);
        }

        if (!isbn.matches("\\d{3}-\\d-\\d{2}-\\d{6}-\\d")) {
            return new Response("Formato de ISBN inválido (XXX-X-XX-XXXXXX-X)", StatusCode.BAD_REQUEST);
        }

        if (bookRepository.exists(isbn)) {
            return new Response("El ISBN ya está registrado", StatusCode.BAD_REQUEST);
        }

        if (!List.of("IMPRESO", "DIGITAL", "AUDIO").contains(format)) {
            return new Response("Formato de libro inválido", StatusCode.BAD_REQUEST);
        }

        if (value <= 0) {
            return new Response("El valor debe ser mayor que 0", StatusCode.BAD_REQUEST);
        }

        Publisher publisher = publisherRepository.findByNit(nitPublisher);
        if (publisher == null) {
            return new Response("Editorial no encontrada", StatusCode.NOT_FOUND);
        }

        if (authorIds == null || authorIds.isEmpty()) {
            return new Response("Debe seleccionar al menos un autor", StatusCode.BAD_REQUEST);
        }

        List<Author> authors = new ArrayList<>();
        Set<Long> unique = new HashSet<>();

        for (Long id : authorIds) {
            if (!unique.add(id)) {
                return new Response("No se permiten autores duplicados", StatusCode.BAD_REQUEST);
            }

            Author author = authorRepository.findById(id);
            if (author == null) {
                return new Response("Autor no encontrado: " + id, StatusCode.NOT_FOUND);
            }

            authors.add(author);
        }

        Book newBook;

        switch (format) {

            case "IMPRESO":
                if (pages <= 0) {
                    return new Response("Las páginas deben ser mayores que 0", StatusCode.BAD_REQUEST);
                }
                if (copies <= 0) {
                    return new Response("Las copias deben ser mayores que 0", StatusCode.BAD_REQUEST);
                }
                newBook = new PrintedBook(title, new ArrayList<>(authors), isbn, genre,
                        "IMPRESO", value, publisher, pages, copies);
                break;

            case "DIGITAL":
                if (hyperlink == null) hyperlink = "";
                newBook = new DigitalBook(title, new ArrayList<>(authors), isbn, genre,
                        "DIGITAL", value, publisher, hyperlink);
                break;

            case "AUDIO":
                if (duration <= 0) {
                    return new Response("La duración debe ser mayor que 0", StatusCode.BAD_REQUEST);
                }
                if (narrator == null) {
                    return new Response("Debe seleccionar un narrador", StatusCode.BAD_REQUEST);
                }
                newBook = new AudioBook(title, new ArrayList<>(authors), isbn, genre,
                        "AUDIO", value, publisher, duration, narrator);
                break;

            default:
                return new Response("Formato inválido", StatusCode.BAD_REQUEST);
        }

        bookRepository.add(newBook);

        Book copy = bookRepository.findByIsbn(isbn);

        return new Response("Libro creado exitosamente", StatusCode.CREATED, copy);
    }
}
