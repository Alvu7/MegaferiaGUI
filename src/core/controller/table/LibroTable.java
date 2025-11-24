package core.controller.table;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.libro.Book;
import core.model.persona.Author;
import core.repository.BookRepository;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class LibroTable {

    public static Response updateLibroTable(DefaultTableModel model, String search) {
        try {
            model.setRowCount(0);

            ArrayList<Book> books = BookRepository.getInstance().getAll();

            boolean filter = (search != null && !search.equalsIgnoreCase("Todos") && !search.trim().isEmpty());
            
            for (Book book : books) {

                if (filter) {
                    String s = search.toLowerCase();

                    boolean matches =
                            book.getIsbn().toLowerCase().contains(s) ||
                            book.getTitle().toLowerCase().contains(s);

                    if (!matches) continue;
                }

                StringBuilder authorsBuilder = new StringBuilder();
                for (Author author : book.getAuthors()) {
                    authorsBuilder.append(author.getFirstName())
                                  .append(" ")
                                  .append(author.getLastName())
                                  .append(", ");
                }

                String authors = authorsBuilder.length() > 2
                        ? authorsBuilder.substring(0, authorsBuilder.length() - 2)
                        : "";

                model.addRow(new Object[]{
                        book.getIsbn(),
                        book.getTitle(),
                        book.getGenre(),
                        book.getFormat(),
                        book.getValue(),
                        book.getPublisher().getName(),
                        authors
                });
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
