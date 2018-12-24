package cl.ucn.disc.dsm.cafa.newsapp.model;

import java.util.List;

import lombok.Getter;

/**
 * Clase que representa la respuesta de una consulta a la API.
 */
public class NewsAPIResponse {
    /**
     * El estado de la consulta. Los valores que toma son 'ok' en caso de una consulta exitosa
     * y 'error' en caso de producirse un error.
     */
    @Getter
    private String status;

    /**
     * La cantidad de articulos obtenidos.
     */
    @Getter
    private Integer totalResults;

    /**
     * La lista de articulos obtenidos.
     */
    @Getter
    private List<Article> articles = null;
}
