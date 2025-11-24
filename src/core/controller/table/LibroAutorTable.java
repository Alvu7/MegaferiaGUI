package core.controller.table;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.libro.AudioBook;
import core.model.libro.Book;
import core.model.libro.DigitalBook;
import core.model.libro.PrintedBook;
import core.model.persona.Author;
import core.repository.AuthorRepository;
import core.repository.BookRepository;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class LibroAutorTable {

    public static Response updateLibroAutorConAdd(DefaultTableModel model, String authorData) {
        try {
            model.setRowCount(0);

            long authorId;
            try {
                authorId = Long.parseLong(authorData.split(" - ")[0]);
            } catch (Exception e) {
                return new Response("Autor inválido", StatusCode.BAD_REQUEST);
            }

            Author author = AuthorRepository.getInstance().findById(authorId);
            if (author == null) {
                return new Response("Autor no encontrado", StatusCode.NOT_FOUND);
            }

            ArrayList<Book> books = BookRepository.getInstance().getAll();

            for (Book book : books) {
                boolean tieneAutor = book.getAuthors()
                        .stream()
                        .anyMatch(a -> a.getId() == author.getId());

                if (!tieneAutor) {
                    continue;
                }

                StringBuilder autoresStr = new StringBuilder();
                for (Author a : book.getAuthors()) {
                    autoresStr.append(a.getFirstName())
                            .append(" ")
                            .append(a.getLastName())
                            .append(", ");
                }

                if (autoresStr.length() > 2) {
                    autoresStr.setLength(autoresStr.length() - 2);
                }

                model.addRow(new Object[]{
                    book.getTitle(),
                    autoresStr.toString(),
                    book.getIsbn(),
                    book.getGenre(),
                    book.getFormat(),
                    book.getValue(),
                    book.getPublisher().getName(),
                    book instanceof PrintedBook ? ((PrintedBook) book).getCopies() : "",
                    book instanceof PrintedBook ? ((PrintedBook) book).getPages() : "", 
                    book instanceof DigitalBook ? ((DigitalBook) book).getHyperlink() : "",
                    book instanceof AudioBook ? ((AudioBook) book).getNarrator().getFirstName() + " " + ((AudioBook) book).getNarrator().getLastName() : "",
                    book instanceof AudioBook ? ((AudioBook) book).getDuration() : ""
                });
            }

            return new Response(
                    "Tabla filtrada por autor correctamente",
                    StatusCode.OK
            );

        } catch (Exception e) {
            return new Response(
                    "Error al filtrar libros por autor: " + e.getMessage(),
                    StatusCode.INTERNAL_SERVER_ERROR
            );
        }
    }
}



