package core.controller.table;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.repository.StandRepository;
import core.model.stands.Stand;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class StandTable {

    public static Response updateStandTable(DefaultTableModel model) {
        try {
            model.setRowCount(0);

            ArrayList<Stand> stands = StandRepository.getInstance().getAll();

            for (Stand stand : stands) {
                Object[] row = {
                        stand.getId(),
                        stand.getPrice(),
                        stand.getPublisherQuantity()
                };

                model.addRow(row);
            }

            return new Response("Tabla de stands actualizada correctamente", StatusCode.OK);

        } catch (Exception e) {
            return new Response(
                    "Error al actualizar tabla de stands: " + e.getMessage(),
                    StatusCode.INTERNAL_SERVER_ERROR
            );
        }
    }
}
