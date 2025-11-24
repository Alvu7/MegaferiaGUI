package core.controller.table;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.libro.Book;
import core.model.persona.Author;
import core.repository.BookRepository;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class LibroFormatoTable {

    public static Response updateLibroFormatoConAdd(DefaultTableModel model, String format) {
        try {
            model.setRowCount(0);

            ArrayList<Book> books = BookRepository.getInstance().getAll();

            for (Book book : books) {

                if (!book.getFormat().equalsIgnoreCase(format)) {
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

            return new Response("Tabla filtrada por formato correctamente", StatusCode.OK);

        } catch (Exception e) {
            return new Response(
                    "Error al filtrar libros por formato: " + e.getMessage(),
                    StatusCode.INTERNAL_SERVER_ERROR
            );
        }
    }
}
