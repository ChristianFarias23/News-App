package cl.ucn.disc.dsm.cafa.newsapp.model;

import lombok.Getter;

/**
 * Clase que representa la fuente de un articulo.
 */
public class Source {

    /**
     * El id de la fuente.
     */
    @Getter
    private String id;

    /**
     * El nombre de la fuente.
     */
    @Getter
    private String name;
}
