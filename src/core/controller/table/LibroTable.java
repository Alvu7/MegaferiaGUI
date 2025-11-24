package core.controller.table;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.libro.Book;
import core.repository.BookRepository;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class LibroTable {

    public static Response updateLibroTable(DefaultTableModel model, String search) {
        try {
            model.setRowCount(0);

            ArrayList<Book> books = BookRepository.getInstance().getAll();

            for (Book book : books) {

                if (search != null && !search.equalsIgnoreCase("Todos")) {
                    if (!book.getIsbn().equalsIgnoreCase(search) &&
                        !book.getTitle().equalsIgnoreCase(search)) {
                        continue;
                    }
                }

                String authors = "";
                for (var author : book.getAuthors()) {
                    authors += author.getFirstName() + " " + author.getLastName() + ", ";
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

            return new Response("Tabla de libros actualizada correctamente", StatusCode.OK);

        } catch (Exception e) {
            return new Response(
                    "Error al actualizar tabla de libros: " + e.getMessage(),
                    StatusCode.INTERNAL_SERVER_ERROR
            );
        }
    }
}
