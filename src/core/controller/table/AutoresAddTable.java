      package core.controller.table;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.persona.Author;
import core.repository.AuthorRepository;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class AutoresAddTable {

    public static Response updateAutoresConAdd(DefaultTableModel model) {
        try {
            model.setRowCount(0);

            ArrayList<Author> autores =
                    AuthorRepository.getInstance().getAllOrderedById();

            for (Author a : autores) {

                Object[] row = {
                        a.getId(),
                        a.getFirstName(),
                        a.getLastName(),
                        a.getBooks().size()
                };

                model.addRow(row);
            }

            return new Response("Tabla de autores actualizada correctamente", StatusCode.OK);

        } catch (Exception e) {
            return new Response(
                    "Error al actualizar tabla de autores: " + e.getMessage(),
                    StatusCode.INTERNAL_SERVER_ERROR
            );
        }
    }
}
