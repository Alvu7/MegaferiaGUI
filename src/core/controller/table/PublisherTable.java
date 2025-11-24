package core.controller.table;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.editoriales.Publisher;
import core.repository.PublisherRepository;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class PublisherTable {

    public static Response updatePublisherTable(DefaultTableModel model) {
        try {
            model.setRowCount(0);

            ArrayList<Publisher> editoriales =
                    PublisherRepository.getInstance().getAll();

            for (Publisher p : editoriales) {
                Object[] row = {
                        p.getNit(),
                        p.getName(),
                        p.getAddress(),
                        p.getManager().getFirstName() + " " + p.getManager().getLastName(),
                        p.getBookQuantity(),
                        p.getStandQuantity()
                };

                model.addRow(row);
            }

            return new Response("Tabla de editoriales actualizada", StatusCode.OK);

        } catch (Exception e) {
            return new Response("Error al actualizar tabla: " + e.getMessage(),
                    StatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
