package cl.ucn.disc.dsm.cafa.newsapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import cl.ucn.disc.dsm.cafa.newsapp.model.Article;

@Dao
public interface ArticleDao {

    /**
     * Inserta un articulo en la base de datos. Si el articulo esta repetido, lo reemplaza.
     * @param article
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    /**
     * Borra todos los articulos en la base de datos.
     */
    @Query("DELETE FROM article")
    void deleteAll();

    /**
     * Obtiene todos los articulos en la base de datos.
     * @return
     */
    @Query("SELECT * FROM article ORDER BY publishedAt DESC")
    LiveData<List<Article>> getAllArticles();


    /**
     * Obtiene la cantidad de articulos en la base de datos.
     * @return
     */
    @Query("SELECT COUNT(*) FROM article")
    int count();

}
