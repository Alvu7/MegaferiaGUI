package core.controller.table;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.persona.Author;
import core.model.persona.Manager;
import core.model.persona.Narrator;
import core.model.persona.Person;
import core.repository.AuthorRepository;
import core.repository.ManagerRepository;
import core.repository.NarratorRepository;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class PersonaTable {

    public static Response updatePersonaTable(DefaultTableModel model) {
        try {
            model.setRowCount(0);

            List<Person> people = new ArrayList<>();

            people.addAll(AuthorRepository.getInstance().getAllOrderedById());
            people.addAll(ManagerRepository.getInstance().getAllOrderedById());
            people.addAll(NarratorRepository.getInstance().getAllOrderedById());

            people.sort((a, b) -> Long.compare(a.getId(), b.getId()));

            for (Person p : people) {

                String tipo =
                        (p instanceof Author) ? "Autor" :
                        (p instanceof Manager) ? "Gerente" :
                        "Narrador";

                model.addRow(new Object[]{
                        p.getId(),
                        p.getFirstName(),
                        p.getLastName(),
                        tipo
                });
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
