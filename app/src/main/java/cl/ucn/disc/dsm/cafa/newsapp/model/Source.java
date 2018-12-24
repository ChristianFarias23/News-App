package cl.ucn.disc.dsm.cafa.newsapp.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa la fuente de un articulo.
 */
public class Source {

    /**
     * El id de la fuente.
     */
    @Getter
    @Setter
    private String id;

    /**
     * El nombre de la fuente.
     */
    @Getter
    @Setter
    private String name;
}
