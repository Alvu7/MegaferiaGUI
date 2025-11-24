package core.controller.table;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.libro.Book;
import core.model.persona.Author;
import core.repository.AuthorRepository;
import core.repository.BookRepository;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

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

                if (!book.getAuthors().contains(author)) {
                    continue;
                }

                String authors = "";
                for (Author a : book.getAuthors()) {
                    authors += a.getFirstName() + " " + a.getLastName() + ", ";
                }

                if (!authors.isEmpty()) {
                    authors = authors.substring(0, authors.length() - 2);
                }

                Object[] row = {
                        book.getIsbn(),
                        book.getTitle(),
                        book.getGenre(),
                        book.getFormat(),
                        book.getValue(),
                        book.getPublisher().getName(),
                        authors
                };

                model.addRow(row);
            }

            return new Response("Tabla filtrada por autor correctamente", StatusCode.OK);

        } catch (Exception e) {
            return new Response(
                    "Error al filtrar libros por autor: " + e.getMessage(),
                    StatusCode.INTERNAL_SERVER_ERROR
            );
        }
    }
}
