package core.controller.table;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.libro.Book;
import core.model.persona.Author;
import core.repository.BookRepository;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class LibroFormatoTable {

    public static Response updateLibroFormatoConAdd(DefaultTableModel model, String format) {
        try {
            model.setRowCount(0);

            ArrayList<Book> books = BookRepository.getInstance().getAll();

            for (Book book : books) {

                if (!book.getFormat().equalsIgnoreCase(format)) {
                    continue;
                }

                StringBuilder authorsStr = new StringBuilder();
                for (Author a : book.getAuthors()) {
                    authorsStr.append(a.getFirstName())
                              .append(" ")
                              .append(a.getLastName())
                              .append(", ");
                }

                if (authorsStr.length() > 2) {
                    authorsStr.setLength(authorsStr.length() - 2);
                }

                model.addRow(new Object[]{
                        book.getIsbn(),
                        book.getTitle(),
                        book.getGenre(),
                        book.getFormat(),
                        book.getValue(),
                        book.getPublisher().getName(),
                        authorsStr.toString()
                });
            }

            return new Response(
                    "Tabla filtrada por formato correctamente",
                    StatusCode.OK
            );

        } catch (Exception e) {
            return new Response(
                    "Error al filtrar libros por formato: " + e.getMessage(),
                    StatusCode.INTERNAL_SERVER_ERROR
            );
        }
    }
}
