package core.controller.table;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.repository.StandRepository;
import core.model.stands.Stand;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class StandTable {

    public static Response updateStandTable(DefaultTableModel model) {
        try {
            model.setRowCount(0);

            ArrayList<Stand> stands = StandRepository.getInstance().getAllOrderedById();

            for (Stand stand : stands) {

                model.addRow(new Object[]{
                        stand.getId(),
                        stand.getPrice(),
                        stand.getPublisherQuantity()
                });
            }

            return new Response(
                    "Tabla de stands actualizada correctamente",
                    StatusCode.OK
            );

        } catch (Exception e) {
            return new Response(
                    "Error al actualizar tabla de stands: " + e.getMessage(),
                    StatusCode.INTERNAL_SERVER_ERROR
            );
        }
    }
}

