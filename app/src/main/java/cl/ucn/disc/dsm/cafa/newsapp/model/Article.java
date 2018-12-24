package cl.ucn.disc.dsm.cafa.newsapp.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa un articulo (noticia) a mostrar en la app.
 */
@Entity(tableName = "article")
public class Article {

    /**
     * PK
     */
    @PrimaryKey(autoGenerate = true)
    @Getter
    @Setter
    private int id;

    /**
     * La fuente del articulo.
     */
    @Embedded(prefix = "src_")
    @Getter
    @Setter
    private Source source;

    /**
     * El autor del articulo.
     */
    @Getter
    @Setter
    private String author;

    /**
     * El titulo.
     */
    @Getter
    @Setter
    private String title;

    /**
     * Una breve descripcion del articulo.
     */
    @Getter
    @Setter
    private String description;

    /**
     * La direccion al articulo.
     */
    @Getter
    @Setter
    private String url;

    /**
     * La direccion a la imagen del articulo.
     */
    @Getter
    @Setter
    private String urlToImage;

    /**
     * La fecha de publicacion.
     */
    @Getter
    @Setter
    private String publishedAt;

    /**
     * El contenido del articulo.
     */
    @Getter
    @Setter
    private String content;

}
