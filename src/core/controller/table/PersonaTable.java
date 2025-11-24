package core.controller.table;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.persona.Author;
import core.model.persona.Manager;
import core.model.persona.Narrator;
import core.repository.AuthorRepository;
import core.repository.ManagerRepository;
import core.repository.NarratorRepository;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class PersonaTable {

    public static Response updatePersonaTable(DefaultTableModel model) {
        try {
            model.setRowCount(0);

            ArrayList<Author> autores =
                    AuthorRepository.getInstance().getAllOrderedById();

            for (Author a : autores) {
                Object[] row = {
                        a.getId(),
                        a.getFirstName(),
                        a.getLastName(),
                        "Autor"
                };
                model.addRow(row);
            }

            ArrayList<Manager> gerentes =
                    ManagerRepository.getInstance().getAllOrderedById();

            for (Manager g : gerentes) {
                Object[] row = {
                        g.getId(),
                        g.getFirstName(),
                        g.getLastName(),
                        "Gerente"
                };
                model.addRow(row);
            }

            ArrayList<Narrator> narradores =
                    NarratorRepository.getInstance().getAllOrderedById();

            for (Narrator n : narradores) {
                Object[] row = {
                        n.getId(),
                        n.getFirstName(),
                        n.getLastName(),
                        "Narrador"
                };
                model.addRow(row);
            }

            return new Response("Tabla de personas actualizada correctamente", StatusCode.OK);

        } catch (Exception e) {
            return new Response(
                    "Error al actualizar la tabla: " + e.getMessage(),
                    StatusCode.INTERNAL_SERVER_ERROR
            );
        }
    }
}
